package vn.tqd.mobilemall.shipmentservice.utils;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserCurrent {
    private String userID;
    private String email;
}
