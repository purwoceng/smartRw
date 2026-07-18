package com.codean.smart_rw.scheduler;

import com.codean.smart_rw.mapper.JadwalRondaMapper;
import com.codean.smart_rw.mapper.JadwalRondaUserMapper;
import com.codean.smart_rw.mapper.UsersMapper;
import com.codean.smart_rw.messaging.NotificationProducer;
import com.codean.smart_rw.model.dto.NotifikasiMessage;
import com.codean.smart_rw.model.pojo.JadwalRondaUserPojo;
import com.codean.smart_rw.model.pojo.UsersPojo;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RondaSchedulerService {
    private final JadwalRondaUserMapper jadwalRondaUserMapper;

    private final JadwalRondaMapper jadwalRondaMapper;

    private final UsersMapper usersMapper;

    private final NotificationProducer notificationProducer;

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

            NotifikasiMessage msg = new NotifikasiMessage();
            msg.setChatId(user.getChatId());
            msg.setPesan(message);
            msg.setUserId(user.getUserId());
            msg.setJenis("Notif Scheduler");

            notificationProducer.publish(msg);
        }
    }
}
