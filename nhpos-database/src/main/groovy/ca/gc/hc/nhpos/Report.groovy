package ca.gc.hc.nhpos

import java.text.SimpleDateFormat

def currentDatetime(){
    def date = new Date()
    sdf = new java.text.SimpleDateFormat("dd-MM-yyyy-hh-mm")
    return sdf.format(date)
}