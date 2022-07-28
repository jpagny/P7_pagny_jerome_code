package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurvePointDTO {

    private Integer curveId;
    private Double term;
    private Double value;

}
