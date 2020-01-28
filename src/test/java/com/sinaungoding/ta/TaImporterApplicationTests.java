package com.sinaungoding.ta;

import com.sinaungoding.jadwal.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class TaImporterApplicationTests {

    @Test
    void readDataTest() {
        Resource resource = new ClassPathResource("jadwal/DOSEN-KODE.csv");
        Generator generator = new Generator();
        try {
            generator.readData(resource.getFile(), Generator.DATA.DOSEN);

//            resource = new ClassPathResource("jadwal/Data-Proposal-Skripsi-D4.csv");
            resource = new ClassPathResource("jadwal/Jadwal-Seminar-Tahap2.csv");
            generator.readData(resource.getFile(), Generator.DATA.PROPOSAL);
//            List<Proposal> proposals = generator.getProposals();
//            proposals.forEach(proposal -> {
//                log.error(proposal.getNim());
//                log.error(proposal.getNama());
//                log.error(proposal.getJudul());
//            });

            resource = new ClassPathResource("jadwal/Tahap2.csv");
            generator.readData(resource.getFile(), Generator.DATA.TEMPLATE);
            List<Template> templates = generator.getTemplates();
//            log.warn("" + templates.size());
//            for (Template t : templates) {
//                log.warn(t.getNim());
//                log.warn(t.getRuang());
//                log.warn(t.getPembimbing());
//            }

            List<TemplateJadwal> templateJadwals = new ArrayList<>();
            templates.forEach(template -> {
                try {
                    TemplateJadwal jadwal = new TemplateJadwal();
                    jadwal.setNim(template.getNim());
                    Proposal proposal = generator.findByNim(template.getNim());
                    jadwal.setNama(proposal.getNama());
                    jadwal.setJudul(proposal.getJudul());
                    Dosen pembimbing = generator.findByKode(template.getPembimbing());
                    jadwal.setPembimbing(pembimbing.getNama());
                    jadwal.setRuang(template.getRuang());
                    jadwal.setSesi(template.getSesi());
                    Dosen penguji = generator.findByKode(template.getPenguji());
//                log.warn(penguji.getNama());
                    jadwal.setPenguji(penguji.getNama());
//                log.warn(template.getPenguji2());
                    penguji = generator.findByKode(template.getPenguji2());
                    jadwal.setPenguji2(penguji.getNama());
                    jadwal.setGrup(generator.getRisetGrup(template.getPenguji().charAt(0)));
                    templateJadwals.add(jadwal);
                } catch (Exception e) {
                    log.error(template.getNim());
                    log.error(e.getMessage(), e);
                }

            });
            if (!templateJadwals.isEmpty()) {
//                String path = System.getProperty("user.dir");
//                File file = new File(path + File.pathSeparator + "output.txt");
                File file = new File("output.txt");
                generator.writeFile(templateJadwals, file);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
