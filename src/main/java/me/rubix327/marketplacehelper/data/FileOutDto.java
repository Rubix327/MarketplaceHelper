package me.rubix327.marketplacehelper.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class FileOutDto {

    private final File file;
    private final String originalName;
    private final String description;

    public static FileOutDto fromFileInputStreamDto(File file, FileInputStreamDto dto){
        return new FileOutDto(file, dto.getOriginalName(), dto.getDescription());
    }
}
