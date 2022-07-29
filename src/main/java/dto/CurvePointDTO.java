package dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurvePointDTO {

    private Integer id;

    @NotNull
    @Min(0)
    private Integer curveId;
    private Timestamp asOfDate;

    @NotNull
    @Min(value = 0, message = "Term must be positive")
    private Double term;

    @NotNull
    @Min(value = 0, message = "Value must be positive")
    private Double value;

    private Timestamp creationDate;

}
