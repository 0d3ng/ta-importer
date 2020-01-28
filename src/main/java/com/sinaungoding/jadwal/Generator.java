package com.sinaungoding.jadwal;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Data
public class Generator {

    public enum DATA {
        DOSEN,
        PROPOSAL,
        TEMPLATE
    }

    private List<Dosen> dosens = new ArrayList<>();
    private List<Proposal> proposals = new ArrayList<>();
    private List<Template> templates = new ArrayList<>();

    public void readData(File file, DATA data) throws Exception {
        List<String> strings = readLines(file);
        if (strings != null) {
            switch (data) {
                case DOSEN:
                    strings.forEach(dt -> {
                        Dosen dosen = new Dosen();
                        dosen.setKode(dt.split(",")[0]);
                        String name = dt.substring(dt.indexOf(",") + 1);
                        if (!name.startsWith("\"") && !name.endsWith("\""))
                            dosen.setNama(name);
                        else
                            dosen.setNama(name.substring(1, name.length() - 1));
                        dosens.add(dosen);
                    });
                    break;
                case PROPOSAL:
                    strings.forEach(dt -> {
                        Proposal proposal = new Proposal();
                        proposal.setNim(dt.split(",")[2]);
                        proposal.setNama(dt.split(",")[3]);
//                        String judul = dt.split(",")[7];
                        String judul = dt.split(",")[4];//tahap 2
                        if (judul.startsWith("\""))
                            proposal.setJudul(judul.substring(1));
                        if (judul.endsWith("\""))
                            proposal.setJudul(judul.substring(0, judul.length() - 1));
                        if (judul.startsWith("\"") && judul.endsWith("\""))
                            proposal.setJudul(judul.substring(1, judul.length() - 1));
                        else
                            proposal.setJudul(judul);
                        proposals.add(proposal);
                    });
                    break;
                case TEMPLATE:
                    strings.forEach(dt -> {
                        try {
                            Template template = new Template();
                            template.setRuang(dt.split(",")[0]);
                            template.setNim(dt.split(",")[2]);
                            template.setSesi(dt.split(",")[1]);
                            template.setPembimbing(dt.split(",")[3]);
                            template.setPenguji(dt.split(",")[4]);
                            template.setPenguji2(dt.split(",")[5]);
                            templates.add(template);
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    });
                    break;
            }
        }

    }

    public Proposal findByNim(String nim) {
        for (Proposal proposal : proposals) {
            if (proposal.getNim().equals(nim)) {
                return proposal;
            }
        }
        return null;
    }

    public Dosen findByKode(String kode) {
        for (Dosen dosen : dosens) {
            if (dosen.getKode().substring(2).equals(kode.substring(2))) {
                return dosen;
            }
        }
        return null;
    }

    private List<String> readLines(File file) throws Exception {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();//ignore header
        while ((line = reader.readLine()) != null) {
//            log.info(line);
            lines.add(line);
        }
        if (reader != null) {
            reader.close();
        }
        return lines.isEmpty() ? null : lines;
    }

    private String listToString(List<TemplateJadwal> templateJadwals) {
        StringBuffer buffer = new StringBuffer();
        templateJadwals.forEach(templateJadwal -> {
            buffer.append(templateJadwal.getSesi());
            buffer.append(";");
            buffer.append(templateJadwal.getRuang());
            buffer.append(";");
            buffer.append(templateJadwal.getNim());
            buffer.append(";");
            buffer.append(templateJadwal.getNama());
            buffer.append(";");
            buffer.append(templateJadwal.getJudul());
            buffer.append(";");
            buffer.append(templateJadwal.getPembimbing());
            buffer.append(";");
            buffer.append(templateJadwal.getGrup());
            buffer.append(";");
            buffer.append(templateJadwal.getPenguji());
            buffer.append(";");
            buffer.append(templateJadwal.getPenguji2());
            buffer.append("\n");
        });
        return buffer.toString();
    }

    public void writeFile(List<TemplateJadwal> templateJadwals, File file) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        String data = listToString(templateJadwals);
        writer.write(data);
        writer.flush();
//        log.info(data);
        if (writer != null) {
            writer.close();
        }
    }

    public String getRisetGrup(char i) {
        switch (i) {
            case 'C':
                return "SISTEM CERDAS";
            case 'I':
                return "INFORMATION SYSTEM";
            case 'V':
                return "COMPUTER VISION";
            case 'M':
                return "MULTIMEDIA AND GAME";
            case 'J':
                return "COMPUTER NETWORK, ARCHITECTURE AND DATA SECURITY";
        }
        return null;
    }

}
