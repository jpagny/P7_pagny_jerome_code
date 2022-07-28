package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class CurvePointDTO {

    @NotNull
    @Min(0)
    private Integer curveId;

    @CreationTimestamp
    private Timestamp asOfDate;

    @NotNull
    @Min(value = 0)
    private Double term;

    @NotNull
    @Min(value = 0)
    private Double value;


}
