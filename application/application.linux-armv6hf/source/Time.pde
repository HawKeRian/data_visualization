class Time{
  int second, minute, clocks;
  int date;
  int start_date;
  String[] datesWeek;
  
  Time(){
    second=0;
    minute=0;
    clocks=0;
    date = 15;
    start_date = 0;
    datesWeek = new String[7];
    datesWeek[0] = "MON";
    datesWeek[1] = "TUE";
    datesWeek[2] = "WED";
    datesWeek[3] = "THU";
    datesWeek[4] = "FRI";
    datesWeek[5] = "SAT";
    datesWeek[6] = "SUN";
  }
  
  void clocksUpdate(){
    clocks += 3;
  }
  
  void clocksTicking(){
    if(clocks == 60){
      //println(minute," :: ",second);
      second++;
      clocks=0;
    }
    if(second==20){ //reduce this condition to get a faster code.
      minute++;
      second=0;
    }
    if(minute==24){
      minute=0;
      date +=1;
      start_date += 1;
      continueDate();
    }
  }
  
  void continueDate(){
    if(start_date > 6){
        start_date =0;
        date = 15;
    }
    startRun = loadStrings(url_output[times.start_date]);
    runTime = loadStrings(url_timeOutput[times.start_date]);
    
    getTripsRun = splitTokens(startRun[c], "[");
    getRunTime = splitTokens(runTime[c],",\"");
    
    // Trip Initialize
    tripsRun = new TripRun[getTripsRun.length];
    for(int i=0; i<tripsRun.length; i++){
      tripsRun[i] = new TripRun(getRunTime[i]);
    }
    
    //Analytic's graph Initialize
    interacts = new Interact();
    analytics = new Graph[24];
    barHeights = new int[24];
    for(int i=0; i<analytics.length; i++){
      analytics[i] = new Graph(times.minute, getRunTime, i);
    }
  }
  
  void selectHour(){
    if(decreaseButton){
      if(times.minute > 0){
        times.minute -= 1;
        times.second =0;
        times.clocks =0;

      }else{ times.minute =0;}
    }
    if(increaseButton){
      if(times.minute < 24){
        times.minute += 1;
        times.second =0;
        times.clocks =0;
        for(int i=0; i<times.start_date; i++){
          barHeights[times.minute-1] = analytics[times.minute-1].barHeight;
          dateCount[times.start_date] += barHeights[times.minute-1];
        }
      }else{ times.minute =23;}
    }
  }
}
