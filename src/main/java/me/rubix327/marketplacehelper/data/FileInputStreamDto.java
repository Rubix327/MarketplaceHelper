package me.rubix327.marketplacehelper.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@AllArgsConstructor
public class FileInputStreamDto {

    private final InputStream inputStream;
    private final String originalName;
    private final String description;

}
