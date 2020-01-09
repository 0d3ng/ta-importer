package com.sinaungoding.jadwal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TemplateJadwal extends Template {
    private String nama;
    private String judul;
    private String grup;
}
