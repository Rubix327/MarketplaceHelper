package me.rubix327.marketplacehelper.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BundleResult {

    private List<FileOutDto> files;

}
