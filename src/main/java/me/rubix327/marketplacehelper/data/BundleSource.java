package me.rubix327.marketplacehelper.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class BundleSource {

    private List<FileInputStreamDto> files;
    private Map<String, String> parameters;

}
