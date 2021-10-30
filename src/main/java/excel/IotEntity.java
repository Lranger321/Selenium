package excel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@AllArgsConstructor
@Getter
public class IotEntity {

    private String name;
    private String price;
    private List<String> feature;
}
