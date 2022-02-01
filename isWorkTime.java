// This is a painless script for elasticsearch index-patterns

boolean isWeekEnd(def date){
    if (date.getDayOfWeek().getValue() >5){
        return true;
    }
    else{
        return false;
    }
}
boolean isOfficeHour(def date){
    def start = LocalTime.of(8,30);
    def stop = LocalTime.of(17,0);

    def time = date.toLocalTime();
    if (time.isAfter(start) && time.isBefore(stop)){
        return true;
    }
    else{
        return false;
    }

}
LocalDate paques(int year) {
    if (year < 1583) {
        throw new IllegalStateException();
    }
    int n = year % 19;
    int c = year / 100;
    int u = year % 100;
    int s = c / 4;
    int t = c % 4;
    int p = (c + 8) / 25;
    int q = (c - p + 1) / 3;
    int e = (19 * n + c - s - q + 15) % 30;
    int b = u / 4;
    int d = u % 4;
    int L = (32 + 2 * t + 2 * b - e - d) % 7;
    int h = (n + 11 * e + 22 * L) / 451;
    int m = (e + L - 7 * h + 114) / 31;
    int j = (e + L - 7 * h + 114) % 31;

    return LocalDate.of(year, m, j + 1);
}
boolean isFrancePublicHoliday(def date){
    def year = date.getYear();

    def JourDelAn = LocalDate.of(year,1,1);
    def FeteDuTravail = LocalDate.of(year,5,1);
    def Victoire45 = LocalDate.of(year,5,8);
    def FeteNationale = LocalDate.of(year,7,14);
    def Armistice18 = LocalDate.of(year,11,11);
    def Paques = paques(year);
    def LundiDePaques = Paques.plusDays(1);
    def Ascension = Paques.plusDays(39);
    def Pentecote  = Paques.plusDays(49);

    def mydate = date.toLocalDate();

    if (mydate.isEqual(JourDelAn) || mydate.isEqual(FeteDuTravail) || mydate.isEqual(Victoire45) || mydate.isEqual(FeteNationale) || mydate.isEqual(Armistice18) || mydate.isEqual(LundiDePaques) || mydate.isEqual(Ascension) || mydate.isEqual(Pentecote)){
        return true;
    }
    else{
        return false;
    }
}
boolean isWorkTime(def date){
    if (isWeekEnd(date)){
        return false;
    }
    if (!isOfficeHour(date)){
        return false;
    }
    if (isFrancePublicHoliday(date)){
        return false;
    }
    return true;
}

ZonedDateTime timestamp = doc["@timestamp"].value;
ZoneId tz = ZoneId.systemDefault();
LocalDateTime localDate = timestamp.withZoneSameInstant(tz).toLocalDateTime();

emit(isWorkTime(localDate));
