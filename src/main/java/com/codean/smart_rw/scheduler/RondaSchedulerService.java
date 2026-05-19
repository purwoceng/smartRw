package com.codean.smart_rw.scheduler;

import com.codean.smart_rw.mapper.JadwalRondaMapper;
import com.codean.smart_rw.mapper.JadwalRondaUserMapper;
import com.codean.smart_rw.mapper.LogNotifikasiMapper;
import com.codean.smart_rw.mapper.UsersMapper;
import com.codean.smart_rw.model.pojo.JadwalRondaUserPojo;
import com.codean.smart_rw.model.pojo.LogNotifikasiPojo;
import com.codean.smart_rw.model.pojo.UsersPojo;
import com.codean.smart_rw.service.TelegramService;
import com.codean.smart_rw.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RondaSchedulerService {
    private final JadwalRondaUserMapper jadwalRondaUserMapper;

    private final JadwalRondaMapper jadwalRondaMapper;

    private final UsersMapper usersMapper;

    private final TelegramService telegramService;

    private final LogNotifikasiMapper logNotifikasiMapper;

    private static final Logger log = LogManager.getLogger(RondaSchedulerService.class);


    @Scheduled(cron = "0 0 15 * * *")
    public void sendRondaNotification() {
        log.info("Scheduler jalan: {}", LocalTime.now());


        LocalDate today = LocalDate.now();
        LocalTime jam = LocalTime.now().withSecond(0).withNano(0);

        String hari = today.getDayOfWeek().name();

        switch (hari) {
            case "MONDAY" -> hari = "SENIN";
            case "TUESDAY" -> hari = "SELASA";
            case "WEDNESDAY" -> hari = "RABU";
            case "THURSDAY" -> hari = "KAMIS";
            case "FRIDAY" -> hari = "JUMAT";
            case "SATURDAY" -> hari = "SABTU";
            case "SUNDAY" -> hari = "MINGGU";
        }

        List<JadwalRondaUserPojo> jadwal = jadwalRondaUserMapper.findByDays(hari, jam);

        log.info("Hari: {}, Jam sekarang: {}", hari, jam);
        log.info("Jumlah jadwal ketemu: {}", jadwal.size());

        for (JadwalRondaUserPojo j : jadwal) {
            UsersPojo user = usersMapper.findById(j.getUserId());

            if (user.getChatId() == null || user.getChatId().isBlank()) {
                continue;
            }

            LogNotifikasiPojo logNotif = new LogNotifikasiPojo();
            logNotif.setLogId(UUID.randomUUID().toString());
            logNotif.setChannel("Telegram");
            logNotif.setJenisNotifikasi("Notif Scheduller");
            logNotif.setSentAt(new DateHelper().getCurrentTimestamp());
            logNotif.setUserId(user.getUserId());
            logNotif.setStatus("Success");

            try {
                String message = String.format(
                        "*Pengingat Jadwal Ronda*\n\n" +
                                "Halo %s \n" +
                                "Anda dijadwalkan ronda pada hari ini.\n\n" +
                                "Hari   :%s\n\n" +
                                "Jam    : %s\n\n" +
                                "Lokasi :%s\n\n" +
                                "Mohon hadir tepat waktu ",
                        user.getNama(),
                        j.getHari(),
                        j.getJamMulai(),
                        j.getNamaLokasi()
                );

                String response = telegramService.sendMessage(user.getChatId(), message);
                logNotif.setResponseTelegram(response);
            } catch (Exception e) {
                log.error("Error scheduler ronda", e);
                logNotif.setStatus("Failed");
                logNotif.setResponseTelegram(e.getMessage());
            }
            logNotifikasiMapper.insert(logNotif);
        }
    }
}
