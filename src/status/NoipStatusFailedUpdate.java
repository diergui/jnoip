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
public class NoipStatusFailedUpdate {

    private Exception error = null;
    private Date nextUpdate = null;

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public Date getNextUpdate() {
        return nextUpdate;
    }

    public void setNextUpdate(Date nextUpdate) {
        this.nextUpdate = nextUpdate;
    }

}
