package com.indizen.cursoSpring.servicio.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils
{
	private DateUtils() {
	}
	
    private static java.util.Calendar getCalendar(boolean ultimoMilisegundo)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        if(ultimoMilisegundo)
        {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
        }
        else
        {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar;
    }
    
    public static java.sql.Timestamp irAlFinal(Date fecha)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp irAlInicio(Date fecha)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp primerDiaSemana()
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    public static java.sql.Timestamp primerDiaSemana(Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    public static java.sql.Timestamp primerDiaSemanaSiguiente(Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    
    public static java.sql.Timestamp ultimoDiaSemana()
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE, 6);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    public static java.sql.Timestamp ultimoDiaSemana(Date date)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE, 6);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp primerDiaSemanaAnterior()
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE, -7);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp primerDiaSemanaAnterior(Date date)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE, -7);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
     
    public static java.sql.Timestamp ultimoDiaSemanaAnterior()
    {
        Calendar calendar = DateUtils.getCalendar(true);
        calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE, 6);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    
    public static java.sql.Timestamp primerDiaQuincena()
    {
        Calendar calendar = DateUtils.getCalendar(false);
        int diaActual = calendar.get(Calendar.DAY_OF_MONTH);
        if (diaActual < 15)
        {
        	calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        else 
        {
        	calendar.set(Calendar.DAY_OF_MONTH, 15);
        }
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp primerDiaQuincenaSiguiente(Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        calendar.add(Calendar.DATE, 15);
        int diaActual = calendar.get(Calendar.DAY_OF_MONTH);
        if (diaActual < 15)
        {
        	calendar.set(Calendar.DAY_OF_MONTH, 1);
        	
        }
        else 
        {
        	calendar.set(Calendar.DAY_OF_MONTH, 16);
        	
        }
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp primerDiaQuincenaAnterior(Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        calendar.add(Calendar.DATE, -15);
        int diaActual = calendar.get(Calendar.DAY_OF_MONTH);
        if (diaActual < 15)
        {
        	calendar.set(Calendar.DAY_OF_MONTH, 1);
        	
        }
        else 
        {
        	calendar.set(Calendar.DAY_OF_MONTH, 16);
        	
        }
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp primerDiaQuincena(Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        int diaActual = calendar.get(Calendar.DAY_OF_MONTH);
        if (diaActual < 15)
        {
        	calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        else 
        {
        	calendar.set(Calendar.DAY_OF_MONTH, 15);
        }
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp ultimoDiaQuincena()
    {
        Calendar calendar = DateUtils.getCalendar(false);
        int diaActual = calendar.get(Calendar.DAY_OF_MONTH);
        if (diaActual < 15)
        {
        	calendar.set(Calendar.DAY_OF_MONTH, 15);
        }
        else 
        {
        	return DateUtils.ultimoDiaMes();
        }
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    public static java.sql.Timestamp ultimoDiaQuincena(Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        int diaActual = calendar.get(Calendar.DAY_OF_MONTH);
        if (diaActual < 15)
        {
        	calendar.set(Calendar.DAY_OF_MONTH, 15);
        }
        else 
        {
        	return ultimoDiaMes(fecha);
        }
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }

    public static int diasLaborables(Date inicio, Date fin) {
    	Calendar hoy=DateUtils.getCalendar(false);
    	hoy.setTime(inicio);
    	Calendar ultimo=DateUtils.getCalendar(false);
    	ultimo.setTime(fin);
    	ultimo.add(Calendar.DATE, 1);
    	int naturales=0;
    	while(hoy.before(ultimo)){
    		if(hoy.get(Calendar.DAY_OF_WEEK)!=1 && hoy.get(Calendar.DAY_OF_WEEK)!=7){
    			naturales++;
    		}
    		hoy.add(Calendar.DATE, 1);
    	}
    	return naturales;
    	
    }
    
    public static java.sql.Timestamp primerDiaMes()
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    public static java.sql.Timestamp primerDiaMes(Date fecha)
    {
    	Calendar calendar = DateUtils.getCalendar(false);
    	calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    public static java.sql.Timestamp primerDiaMesSiguiente(Date fecha)
    {
    	Calendar calendar = DateUtils.getCalendar(false);
    	calendar.setTime(fecha);
    	calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp ultimoDiaMesSiguiente()
    {
        Calendar calendar = DateUtils.getCalendar(true);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp ultimoDiaMes()
    {
        Calendar calendar = DateUtils.getCalendar(true);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    public static java.sql.Timestamp ultimoDiaMes(Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(true);
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp primerDiaMesAnterior()
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp primerDiaSumandoMeses(int meses)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.add(Calendar.MONTH, meses);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp primerDiaMesAnterior(Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp ultimoDiaMesAnterior()
    {
        Calendar calendar = DateUtils.getCalendar(true);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp primerDiaAnyo()
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp ultimoDiaAnyo()
    {
        Calendar calendar = DateUtils.getCalendar(true);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp convertUtilDateToTimestamp(java.util.Date date)
    {
        java.sql.Timestamp time = null;
        time = new java.sql.Timestamp(date.getTime());
        return time;
    }
    
    public static java.sql.Timestamp primerDiaAnyo(int ayo)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.set(Calendar.YEAR, ayo);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp ultimoDiaAnyo(int ayo)
    {
        Calendar calendar = DateUtils.getCalendar(true);
        calendar.set(Calendar.YEAR, ayo);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp diaAnyoAnterior(Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)-1);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp diaAnyoSiguiente(Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+1);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp diaSumandoMeses(int meses, Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, meses);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static java.sql.Timestamp diaSumandoMinutos(int minutos, Date fecha)
    {
        Calendar calendar = DateUtils.getCalendar(false);
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        return DateUtils.convertUtilDateToTimestamp(calendar.getTime());
    }
    
    public static String obtenerFechaFormato(Date fecha,String pattern){
    	SimpleDateFormat dateFormat = new SimpleDateFormat();
    	dateFormat.applyPattern(pattern);
    	return dateFormat.format(fecha);
    }
}