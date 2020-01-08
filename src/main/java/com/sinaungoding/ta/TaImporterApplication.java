package com.sinaungoding.ta;

import com.sinaungoding.ta.entity.TugasAkhir;
import com.sinaungoding.ta.repository.TugasAkhirRepository;
import com.sinaungoding.ta.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@Slf4j
public class TaImporterApplication implements CommandLineRunner {

    @Autowired
    TugasAkhirRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(TaImporterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Resource resource = new ClassPathResource("Judul-LA-TA.xlsx");
//        List<TugasAkhir> tugasAkhirs = ExcelUtil.read(resource.getFile());
//        tugasAkhirs.forEach(tugasAkhir -> {
//            log.info(tugasAkhir.getJudul());
//            log.info("" + tugasAkhir.getTahun());
//            log.info(tugasAkhir.getJenjang());
//            repository.save(tugasAkhir);
//        });

        Iterable<TugasAkhir> all = repository.findAll();
        List<TugasAkhir> akhirs = new ArrayList<>();
        all.forEach(akhirs::add);
        log.info("" + akhirs.size());

        int LIMIT = 5;

        all.forEach(tugasAkhir -> {
            String judul = tugasAkhir.getJudul();
            String[] s = judul.split(" ", LIMIT);
            StringBuilder builder = new StringBuilder();
            for (String a : s) {
                builder.append(a);
            }
            akhirs.forEach(ta -> {
                String[] ss = judul.split(" ", LIMIT);
                StringBuilder stringBuilder = new StringBuilder();
                for (String b : ss) {
                    stringBuilder.append(b);
                }
                if (builder.toString().toUpperCase().equals(stringBuilder.toString().toUpperCase())) {
                    log.info(ta.getJudul());
                }
            });
        });

    }
}
