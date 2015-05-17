/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package status;

import java.util.Date;

/**
 *
 * @author diego
 */
public class NoipStatusNochange  implements INoipStatus {

    private String ip = null;
    private Date nextUpdate = null;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getNextUpdate() {
        return nextUpdate;
    }

    public void setNextUpdate(Date nextUpdate) {
        this.nextUpdate = nextUpdate;
    }

}
