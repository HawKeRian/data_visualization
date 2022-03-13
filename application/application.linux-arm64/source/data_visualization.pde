TripRun[] tripsRun;
Time times;
Graph[] analytics;
GraphV2[] statistics;
Interact interacts;
Scrollbar scrollButton;


Building[] buildingsDowntown, universityCalgary, hospital, mall, northMall, eastMall, southMall;
Building buildingsDowntowns, hospitals, malls, northMalls, eastMalls, southMalls;

PImage backgroundMap;  // Variable for background map image
PImage backgroundPause;
PImage backgroundStart;
PImage backgroundHelp;
PImage backgroundIncrease;
PImage backgroundDecrease;
PImage backgroundAMPM;
PImage backgroundHelping_1;
PImage backgroundHelping_2;
PImage backgroundHelping_3;
PImage northArrow;

String[] startRun;
String[] runTime;

String url_map = "/picture/Calgary.png";
String url_run = "/data/15072019-output.txt";
String url_runTime = "/data/15072019-timeOutput.txt";

String[] url_output;
String[] url_timeOutput;

String url_pauseButton1 = "/picture/button_b.png";
String url_pauseButton2 = "/picture/button_a.png";
String url_startButton1 = "/picture/start1.png";
String url_startButton2 = "/picture/start2.png";
String url_helpButton1 = "/picture/help1.png";
String url_helpButton2 = "/picture/help2.png";
String url_increaseButton1 = "/picture/increaseButton1.png";
String url_increaseButton2 = "/picture/increaseButton2.png";
String url_decreaseButton1 = "/picture/decreaseButton1.png";
String url_decreaseButton2 = "/picture/decreaseButton2.png";
String url_amButton1 = "/picture/am1.png";
String url_amButton2 = "/picture/am2.png";
String url_pmButton1 = "/picture/pm1.png";
String url_pmButton2 = "/picture/pm2.png";
String url_backgroundHelping_1 = "/picture/HelpingMenu1.png";
String url_backgroundHelping_2 = "/picture/HelpingMenu2.png";
String url_backgroundHelping_3 = "/picture/HelpingMenu3.png";
String url_northArrow = "/picture/north.png";

String quit;
String help;

String showG;
String showI;

String warningRotate="";

float mapGeoLeft   = -114.257275;       // Longitude degree west
float mapGeoRight  = -113.865379;       // Longitude degree east
float mapGeoTop    = 51.183097;       // Latitude degree north
float mapGeoBottom = 50.875688;          // Latitude degree south

float mapScreenWidth, mapScreenHeight;  // Variables for dimension of map in pixels

int c = 0; // Variable for line index
int[] barHeights;
int[] increasing;
int[] dateCount = new int[7];
int[] filterData = new int[0];
int dateDisplay = 15;
int max=0;
int min=15;
int maxDate, minDate;
int lighting=7;

int stat_min, stat_max;
int stat_dateMin, stat_dateMax;
int sumDate, sumWeek, avgDate, avgWeek;
int lineCount =0;

PFont font_date,f1,f2;

float scale =0.75;
float viewX=0;
float viewY=0;
float camX=0;
float camY=0;
float translateX=0;
float translateY=0;
float bgLight = 52.5;
float mapDiameter = 0;

String[] getTripsRun;
String[] tripRunWaypoint;
String[] getRunTime;

boolean Xrotate = false;
boolean Yrotate = false;
boolean flowPoint = true;
boolean zoom = true;

boolean startButton = false;
boolean helpButton = false;
boolean quitButton = false;

boolean showBuildingButton = true;
boolean showGraphButton = true;
boolean showInteractButton = true;
boolean timePauseButton = false;
boolean resetHourButton = false;
boolean lightingButton = true;
boolean pickupButton = true;
boolean dropoffButton = true;
boolean[] selectDateButton = new boolean[7];
boolean[] lineGraphButton = new boolean[7];
boolean lineDefaultButton = true;
boolean expandGraphButton = false;
boolean decreaseButton = false;
boolean increaseButton = false;
boolean okFilterButton = false;
boolean helpingMenuButton = true;

color quitColor = color(255,0,0);
color showBuildingColor = color(#40A0D8);
color lightingColor = color(255,0,0);
color showDestColor = color(255,0,0);
color showGraphColor = color(255,0,0);
color showInteractColor = color(255,0,0);
color pickupColor = color(255,0,0);
color dropoffColor = color(255);
color barColor = color(0,7,0);
color resetHourColor = color(255,0,0);
color resetCameraColor = color(255,0,0);
color[] selectDateColor = new int[7];
color expandGraphColor = color(255,0,0);
color helpingMenuColor = color(200);

void setup(){
  size(1000, 960, P3D);
  smooth();
  
  //-------------------Read all files in dataset folder-------------------
  printAllFileNames();
  
  //-------------------Time Counter Setting-------------------
  times = new Time();
  
  //-------------------Font Setting-------------------
  font_date = createFont("Impact", 15, true);
  f1 = createFont("Impact", 100, true);
  f2 = createFont("Impact", 20, true);
  
  backgroundMap = loadImage(url_map);
  backgroundPause = loadImage(url_pauseButton1);
  backgroundStart = loadImage(url_startButton1);
  backgroundHelp = loadImage(url_helpButton1);
  backgroundIncrease = loadImage(url_increaseButton1);
  backgroundDecrease = loadImage(url_decreaseButton1);
  backgroundAMPM = loadImage(url_amButton1);
  backgroundHelping_1 = loadImage(url_backgroundHelping_1);
  backgroundHelping_2 = loadImage(url_backgroundHelping_2);
  backgroundHelping_3 = loadImage(url_backgroundHelping_3);
  northArrow = loadImage(url_northArrow);
  
  startRun = loadStrings(url_output[times.start_date]);
  runTime = loadStrings(url_timeOutput[times.start_date]);
  
  getTripsRun = splitTokens(startRun[c], "[");
  getRunTime = splitTokens(runTime[c],",\"");
  
  for(int i=0; i<selectDateColor.length; i++){
    selectDateColor[i] = color(175,150);
  }
  
  //-------------------Trip Initialize-------------------
  tripsRun = new TripRun[getTripsRun.length];
  for(int i=0; i<tripsRun.length; i++){
    tripsRun[i] = new TripRun(getRunTime[i]);
  }
  
  //-------------------Analytic's graph Initialize-------------------
  interacts = new Interact();
  vertex(30,(384)-25,0,0);
  analytics = new Graph[24];
  barHeights = new int[24];
  increasing = new int[24];
  for(int i=0; i<analytics.length; i++){
    analytics[i] = new Graph(times.minute, getRunTime, i);
  }
  scrollButton = new Scrollbar(30,485,220,20);
  statistics = new GraphV2[7];
  for(int i=0; i<statistics.length; i++){
    statistics[i] = new GraphV2(i);
    statistics[i].initLineGraph();
  }
  for(int i=0; i<lineGraphButton.length; i++){
    lineGraphButton[i] = true;
  }
}

void draw(){
  //-------------------Set map dimension to dispaly window's width and height-------------------
  mapScreenWidth  = width;
  mapScreenHeight = height;
  
  //-------------------Building Initialize-------------------
    universityCalgary = new Building[2];
    universityCalgary[0] = new Building(-190,-150,4);
    universityCalgary[1] = new Building(-70,-95,2);
    buildingsDowntown = new Building[39];
    buildingsDowntowns = new Building();
    hospital = new Building[16];
    hospitals = new Building();
    mall = new Building[71];
    malls = new Building();
    northMall = new Building[21];
    northMalls = new Building();
    southMall = new Building[11];
    southMalls = new Building();
    
    buildingsDowntowns.initDowntown(buildingsDowntown);
    hospitals.initHospital(hospital);
    malls.initMall(mall);
    northMalls.initNorthMall(northMall);
    southMalls.initSouthMall(southMall);
  
  if(startButton == false){ //-------------------Main Menu-------------------
    background(0);
    textFont(f1, 50);
    fill(255);
    text("Scooter Visualization", (width/2)-200, (height/2)-100);
    drawPattern();
    
    //-------------------Button Part-------------------
    pushMatrix();
      noStroke();
      //start checking
      if((mouseX > (width*0.5)-75 && mouseX <(width*0.5)+75) && (mouseY > (height/2)-35 && mouseY <(height/2)+35)){
            backgroundStart = loadImage(url_startButton2);
          if(mousePressed && (mouseButton == LEFT)){
            startButton = true;
          }
      }else{
          backgroundStart = loadImage(url_startButton1);
      }
      noStroke();
      beginShape(QUADS);
      texture(backgroundStart);
      vertex((width/2)-75,(height/2)-35,0,0);
      vertex((width/2)+75,(height/2)-35,150,0);
      vertex((width/2)+75,(height/2)+35,150,68);
      vertex((width/2)-75,(height/2)+35,0,68);
      endShape();
      
      //-------------------Help Checking-------------------
      if((mouseX > (width*0.5)-75 && mouseX <(width*0.5)+75) && (mouseY > (height/1.7)-35 && mouseY <(height/1.7)+35)){
            backgroundHelp = loadImage(url_helpButton2);
          if(mousePressed && (mouseButton == LEFT)){
            helpButton = true;
          }
      }else{
          backgroundHelp = loadImage(url_helpButton1);
      }
      noStroke();
      beginShape(QUADS);
      texture(backgroundHelp);
      vertex((width/2)-75,(height/1.7)-35,0,0);
      vertex((width/2)+75,(height/1.7)-35,150,0);
      vertex((width/2)+75,(height/1.7)+35,150,68);
      vertex((width/2)-75,(height/1.7)+35,0,68);
      endShape();
  popMatrix();

  }else{
    //-------------------Start Program-------------------
    //
    //-------------------Display Background Map-------------------
    background(bgLight);
    daylight();
    
    pushMatrix();
      //-------------------Zoom into map-------------------
      scale = constrain(scale,0.5,10);
      translate(translateX, translateY);
      scale(scale,scale,scale/2);
      
      //-------------------3D Mapping-------------------;
      if(mapScreenWidth/mapScreenHeight < 1.5){
        mapDiameter = sqrt(pow(mapScreenWidth,2) + pow(mapScreenHeight,2)); //1,386.2178
      }else{
        mapDiameter = sqrt(pow(mapScreenWidth*0.543077,2) + pow(mapScreenHeight,2));
      }
      
      translate((width/2.0-camX)+interacts.mapRight, (height/2.0-camY), -mapDiameter/1.6);
      //translate((width/2.0-camX), (height/2.0-camY));
      rotateX(-75*TWO_PI/height);
      
      //-------------------Rotate Left-to-Right-------------------
      
      rotateLeftRight();
      
      //-------------------Rotate Top-to-Bottom-------------------
      
      rotateTopBot();
      
      drawMap();
      //-------------------Create Building Part-------------------
      if(showBuildingButton){
        for(int i=0; i<buildingsDowntown.length; i++){
          buildingsDowntown[i].drawBuildingTall();
        }
        for(int i=0; i<hospital.length; i++){
          hospital[i].drawBuildingHospital();
        }
        for(int i=0; i<mall.length; i++){
          mall[i].drawBuildingMall();
        }
        for(int i=0; i<universityCalgary.length; i++){
          universityCalgary[i].drawBuildingUniversity();
        }
        for(int i=0; i<northMall.length; i++){
          northMall[i].drawBuildingMall();
        }
        for(int i=0; i<southMall.length; i++){
          southMall[i].drawBuildingMall();
        }
      }
      
      //-------------------Initial TripRunning-------------------
      if(flowPoint){
        for(int i=0; i<getTripsRun.length; i++){
        //for(int i=0; i<1; i++){
          tripRunWaypoint = splitTokens(getTripsRun[i], "{\"LatLongitude\":,}]");
          tripsRun[i].initMap();
          tripsRun[i].initTrip(tripRunWaypoint);
          if(tripsRun[i].time == times.minute){
            pushMatrix();
              if(!timePauseButton){
                tripsRun[i].update();
              }
              tripsRun[i].directionTrip();
            popMatrix();  
          }
        }
      }
    popMatrix();
    
    pushMatrix();
    //-------------------Graph Part-------------------
    if(showInteractButton){
      interacts.interactionInit();
      interacts.miniMap();
      for(int i=0; i<getTripsRun.length; i++){
        tripRunWaypoint = splitTokens(getTripsRun[i], "{\"LatLongitude\":,}]");
        if(tripsRun[i].time == times.minute){
          interacts.originDisplay(tripRunWaypoint);
          interacts.destinationDisplay(tripRunWaypoint);
        }
      }
      if(!timePauseButton){
        times.clocksUpdate();
        times.clocksTicking();
      }
    }
    if(showGraphButton){
      analytics[0].graphSetup();
      for(int i=0; i<analytics.length; i++){
        analytics[i].graphCounter();
        if(times.minute==23 && times.second==19 && times.clocks>50){
          analytics[i].timeCounter=0;
          barHeights = new int[24];
          increasing = new int[24];
        }else{
          if(analytics[i].barHeight >0){
            barHeights[i] = analytics[i].barHeight;
          }
        }
        //if(barHeights[i] > 29){
        //  barHeights[i] = 29;
        //}
      }
      if(times.clocks == 3 && times.second == 0 && times.minute == 0){
        increasing = new int[24];
      }
      
      if(mapScreenWidth/mapScreenHeight < 1.5){
        for(int i=0; i<(analytics.length/2); i++){
          if(increasing[i]<barHeights[i]*7.5){
            increasing[i] += 1;
          }
          pushMatrix();
            translate(155,(height/1.270)-i*13.5);
            fill(barColor,150);
            noStroke();
            rectMode(LEFT);
            rect(0,i+150,increasing[i],i+160);
          popMatrix();
        }
        if(times.minute>=12){
          for(int i=analytics.length/2; i<analytics.length; i++){
            if(increasing[i]<barHeights[i]*7){
              increasing[i] += 1;
            }
            pushMatrix();
              translate(155,(height/1.270)-(i-12)*13.5);
              fill(barColor,150);
              noStroke();
              rectMode(LEFT);
              rect(320,(i-12)+150,320+increasing[i],(i-12)+160);
            popMatrix();
          }
        }
      }else{
        for(int i=0; i<(analytics.length/2); i++){
          if(increasing[i]<barHeights[i]*7.25){
            increasing[i] += 1;
          }
          pushMatrix();
            translate(155,(height/1.270)-i*13.5);
            fill(barColor,150);
            noStroke();
            rectMode(LEFT);
            rect(0,i+155,increasing[i],i+165);
          popMatrix();
        }
        if(times.minute>=12){
          for(int i=analytics.length/2; i<analytics.length; i++){
            if(increasing[i]<barHeights[i]*7.25){
              increasing[i] += 1;
            }
            pushMatrix();
              translate(155,(height/1.270)-(i-12)*13.5);
              fill(barColor,150);
              noStroke();
              rectMode(LEFT);
              rect(320,(i-12)+155,320+increasing[i],(i-12)+165);
            popMatrix();
          }
        }
      }
      
      if(times.second == 0 && times.clocks ==3 ){
        if(!decreaseButton){
          dateCount[times.start_date] = 0;
          for(int i=0; i<times.minute+1; i++){
            dateCount[times.start_date] = dateCount[times.start_date] + barHeights[i];
          }
        }else{
          dateCount[times.start_date] = dateCount[times.start_date] - barHeights[times.minute];
          dateCount[times.start_date] = constrain(dateCount[times.start_date],barHeights[0],500);
        }
      }
    }
    popMatrix();
    
    //-------------------Button Part-------------------
    increaseDecreaseDateButton();
    quitMenuButton();
    showBuildingButton();
    lightingButton();
    showInteractButton();
    showGraphButton();
    pauseButton();
    pick_dropButton();
    resetHourButton();
    resetCameraButton();
    hightlightGraph();
    selectDateButton();
    ampmButton();
    
    scrollButton.update();
    scrollButton.display();
    scrollButton.checkHourSlider();
    
    //-------------------Text Part-------------------
    textPart();
    
    //-------------------Second Graph-------------------
    for(int i=0; i< lineGraphButton.length; i++){
      if(lineGraphButton[i]){
        filterData = append(filterData,i);
      }
    }
    
    if(filterData.length > 0){
      stat_min = statistics[filterData[0]].min;
      stat_dateMin = statistics[filterData[0]].dateMin;
    }else{
      stat_min = -10;
      stat_dateMin = -10;
    }
    
    
    statistics[0].secondGraphBG();
    expandGraph();
    textpartExpand();
    //statistics[0].filterNumberGraph();
    statistics[0].filterLineGraph();
    statistics[0].secondGraphSetup();
    if(okFilterButton){
      if(statistics[0].filterMin > statistics[0].filterMax){
        statistics[0].filterSwap = statistics[0].filterMax;
        statistics[0].filterMax = statistics[0].filterMin;
        statistics[0].filterMin = statistics[0].filterSwap;
      }
      for(int i=0; i< statistics.length; i++){
        for(int j=statistics[0].filterMin; j<statistics[0].filterMax+1; j++){
          statistics[i].secGrph_line(i,j);
        }
      }
    }else{
      for(int i=0; i< statistics.length; i++){
        for(int j=0; j<analytics.length; j++){
          statistics[i].secGrph_line(i,j);
        }
      }
    }

    for(int i=0; i< filterData.length; i++){
      statistics[filterData[i]].graphStatic();
    }
    statisticsData();
    textStatisticsData();
    resetZero();
    hightlightSecondGraph();
  }
  if(helpButton == true){
    background(75);
    quitMenuButton();
    helpingMenuPanel();
    
    if(mapScreenWidth/mapScreenHeight < 1.5){
      if(helpingMenuButton){
        helpingMenu1();
      }else{
        helpingMenu2();
      }
    }else{
      helpingMenu1();
      helpingMenu2();
    }
  }
}

void drawMap(){
  noStroke();
  float drawWidth;
  if(mapScreenWidth/mapScreenHeight > 1.5){
    drawWidth = mapScreenWidth*0.543077;
  }else{
    drawWidth = mapScreenWidth;
  }
 
  beginShape(QUADS);
  texture(backgroundMap);
  vertex(-drawWidth/2,0,-mapScreenHeight/2,0,0);
  vertex(drawWidth/2,0,-mapScreenHeight/2,851,0);
  vertex(drawWidth/2,0,mapScreenHeight/2,851,1058);
  vertex(-drawWidth/2,0,mapScreenHeight/2,0,1058);
  endShape();
  
  beginShape(QUADS);
  texture(northArrow);
  vertex(drawWidth/2.5,-5,-mapScreenHeight/2,0,0);
  vertex(drawWidth/2,-5,-mapScreenHeight/2,504,0);
  vertex(drawWidth/2,-5,-mapScreenHeight/3,504,666);
  vertex(drawWidth/2.5,-5,-mapScreenHeight/3,0,666);
  endShape();
}

void mouseWheel(MouseEvent e){
  translateX -= mouseX;
  translateY -= mouseY;
  float delta = e.getCount();
  if(delta >0 && scale != 10){
    delta = 1.1;
  }else if(delta < 0 && scale != 0.5){
    delta = 1.0/1.05;
  }else{
    delta = 1.0;
  }
  println(scale);
  translateX *= delta;
  translateY *= delta;
  translateX += mouseX;
  translateY += mouseY;
  scale *= delta;
}

void mouseDragged(){
  if(mouseX > 250 && mouseY < height/1.322){
    if(mouseButton == LEFT){
    camX += (pmouseX - mouseX);
    camY += (pmouseY - mouseY);
  }
  translateX += mouseX - pmouseX;
  translateY += mouseY - pmouseY;
  }
}

void mouseClicked(){
  int low;
  int high;
  if(mapScreenWidth/mapScreenHeight > 1.5){
    low = 500;
    high = 530;
  }else{
    low = 490;
    high = 520;
  }
  
  //-------------------Interaction Part-------------------
  if(showInteractButton){
    //Building BUtton
    if((mouseX > 200-37.5 && mouseX < 200+37.5) && (mouseY > (height/1.4)-159 && mouseY <(height/1.4)-129)){
      if(mouseButton == LEFT){
        if(showBuildingButton == true) {
          showBuildingButton = false;
        }else{
          showBuildingButton = true;
        }
      }
    }
    
    //-------------------Light Option Button-------------------
    if((mouseX > 200-37.5 && mouseX < 200+37.5) && (mouseY > (height/1.4)-111 && mouseY <(height/1.4)-81)){
      if(mouseButton == LEFT){
        if(lightingButton == true) {
          lightingButton = false;
        }else{
          lightingButton = true;
        }
      }
    }
    
    //-------------------Reset Hour Button-------------------
    if((mouseX > 200-37.5 && mouseX < 200+37.5) && (mouseY > (height/1.4)-63 && mouseY <(height/1.4)-33)){
      if(mouseButton == LEFT){
        times.minute =0;
        times.second =0;
        times.clocks =0;
        barHeights = new int[24];
        increasing = new int[24];
        dateCount[times.start_date] = 0;
      }
    }
    
    //-------------------Reset View Button-------------------
    if((mouseX > 200-37.5 && mouseX < 200+37.5) && (mouseY > (height/1.4)-15 && mouseY <(height/1.4)+15)){
      if(mouseButton == LEFT){
        viewX = 0;
        viewY = 0;
        scale = 0.75;
        translateX = 0.0;
        translateY = 0.0;
      }
    }
    
    //-------------------Time Pause Button-------------------
    if((mouseX > 30 && mouseX < 90) && (mouseY > (384)-25 && mouseY <(384)+25)){
      if(mouseButton == LEFT){
        if(timePauseButton == true) {
          timePauseButton = false;
        }else{
          timePauseButton = true;
        }
      }
    }
    
    //-------------------Pick-up Button-------------------
    if((mouseX > 40 && mouseX < 60) && (mouseY > interacts.mapBottom-5 && mouseY <interacts.mapBottom+35)){
      if(mouseButton == LEFT){
        if(pickupButton == true) {
          pickupButton = false;
        }else{
          pickupButton = true;
        }
      }
    }
    
    //-------------------Drop-off Button-------------------
    if((mouseX > 140 && mouseX < 160) && (mouseY > interacts.mapBottom-5 && mouseY <interacts.mapBottom+35)){
      if(mouseButton == LEFT){
        if(dropoffButton == true) {
          dropoffButton = false;
        }else{
          dropoffButton = true;
        }
      }
    }
    
    //-------------------SelectDate Button-------------------
    for(int i=0; i<selectDateButton.length; i++){
      if(mapScreenWidth/mapScreenHeight < 1.5){
        if((mouseX > 27.5+(i*30) && mouseX < 52.5+(i*30)) && (mouseY > (436.363636)-12.5) && mouseY < (436.363636)+12.5){
          if(mouseButton == LEFT && times.start_date != i){
            selectDateButton[i] = true;
            times.start_date = 0;
          }
        }
      }else{
        if((mouseX > 27.5+(i*30) && mouseX < 52.5+(i*30)) && (mouseY > (436.363636)-12.5) && mouseY < (436.363636)+12.5){
          if(mouseButton == LEFT && times.start_date != i){
            selectDateButton[i] = true;
            times.start_date = 0;
          }
        }
      }
    }
    
    //-------------------Increase-Decrease Hour Button-------------------
    if((mouseX > 30 && mouseX < 80) && (mouseY > low && mouseY < high )){
      if(mouseButton == LEFT){
        if(decreaseButton == true) {
          decreaseButton = false;
          increaseButton = false;
        }else{
          decreaseButton = true;
          increaseButton = false;
          barHeights[times.minute] = 0;
          increasing[times.minute] = 0;
          times.selectHour();
        }
      }
    }
    
    //-------------------Increase-Decrease Button-------------------
    if((mouseX > 100 && mouseX < 150) && (mouseY > low && mouseY < high )){
      if(mouseButton == LEFT){
        if(increaseButton == true) {
          increaseButton = false;
          decreaseButton = false;
        }else{
          increaseButton = true;
          decreaseButton = false;
          times.selectHour();
        }
      }
    }
    
    //-------------------Change AM-PM mode-------------------
    if((mouseX > 175 && mouseX < 225) && (mouseY > low && mouseY < high )){
      if(mouseButton == LEFT){
        if(times.minute < 12){
          times.minute = 12;
          times.second = 0;
          times.clocks = 0;
        }else{
          times.minute = 0;
          times.second = 0;
          times.clocks = 0;
          dateCount = new int[24];
          barHeights = new int[24];
          increasing = new int[24];
        }
      }
    }
  }
  
  //-------------------ShowGraph Button-------------------
  if((mouseX > 25 && mouseX <75) && (mouseY > (height*0.075)-25 && mouseY <(height*0.075)+25)){
    if(mouseButton == LEFT){
      if(showGraphButton == true) {
        showGraphButton = false;
      }else{
        showGraphButton = true;
      }
    }
  }
  
  //-------------------ShowInteract Button-------------------
  if((mouseX > 95 && mouseX <145) && (mouseY > (height*0.075)-25 && mouseY <(height*0.075)+25)){
    if(mouseButton == LEFT){
      if(showInteractButton == true) {
        showInteractButton = false;
      }else{
        showInteractButton = true;
      }
    }
  }
  
  //-------------------Graph Part-------------------
  if(showGraphButton){
    //expandGraph Button
    if((mouseX > width-97.5 && mouseX < width-22.5) && (mouseY > (height/1.275)-15 && mouseY <(height/1.275)+15)){
      if(mouseButton == LEFT && mapScreenWidth/mapScreenHeight < 1.5){
        if(expandGraphButton == true) {
          expandGraphButton = false;
        }else{
          expandGraphButton = true;
        }
      }
    }
  }
  
  //-------------------Line Graph Part-------------------
  if(expandGraphButton){
    if((mouseX > 875-10 && mouseX < 875+10) && (mouseY > (height/1.2)-10 && mouseY < (height/1.2)+10)){
      if(mouseButton == LEFT){
        statistics[0].filterMin = 0;
        statistics[0].filterMax = 0;
        okFilterButton = false;
        if(lineDefaultButton == false){
          lineDefaultButton = true;
          for(int i=0; i< lineGraphButton.length; i++){
            lineGraphButton[i] = true;
          }
        }
      }
    }
    for(int i=1; i<statistics.length+1; i++){
      if(i-1<(statistics.length-1)/2){
        if((mouseX > 875+(i*30)-10 && mouseX < 875+(i*30)+10) && (mouseY > (height/1.2)-10 && mouseY < (height/1.2)+10)){
          if(mouseButton == LEFT){
            lineDefaultButton = false;
            if(lineGraphButton[i-1] == true){
              lineGraphButton[i-1] = false;
            }else{
              lineGraphButton[i-1] = true;
            }
          }
        }
      }else{
        if((mouseX > 875+((i-4)*30)-10 && mouseX < 875+((i-4)*30)+10) && (mouseY > (height/1.2)+20 && mouseY < (height/1.2)+40)){
          if(mouseButton == LEFT){
            lineDefaultButton = false;
            if(lineGraphButton[i-1] == true){
              lineGraphButton[i-1] = false;
            }else{
              lineGraphButton[i-1] = true;
            }
          }
        }
      }
    }
    if((mouseX > 965-15 && mouseX < 965+15) && (mouseY > (height/1.075)-25) && mouseY < (height/1.075)+25){
      if(mouseButton == LEFT){
        if(okFilterButton == false){
          okFilterButton = true;
        }
      }
    }
    
  }
  if(mapScreenWidth/mapScreenHeight > 1.5){
    if((mouseX > width/2+825-10 && mouseX <  width/2+825+10) && (mouseY > (height/1.2)-10 && mouseY < (height/1.2)+10)){
      if(mouseButton == LEFT){
        statistics[0].filterMin = 0;
        statistics[0].filterMax = 0;
        okFilterButton = false;
        if(lineDefaultButton == false){
          lineDefaultButton = true;
          for(int i=0; i< lineGraphButton.length; i++){
            lineGraphButton[i] = true;
          }
        }
      }
    }
    for(int i=1; i<statistics.length+1; i++){
      if(i-1<(statistics.length-1)/2){
        if((mouseX > width/2+825+(i*30)-10 && mouseX < width/2+825+(i*30)+10) && (mouseY > (height/1.2)-10 && mouseY < (height/1.2)+10)){
          if(mouseButton == LEFT){
            lineDefaultButton = false;
            if(lineGraphButton[i-1] == true){
              lineGraphButton[i-1] = false;
            }else{
              lineGraphButton[i-1] = true;
            }
          }
        }
      }else{
        if((mouseX > width/2+825+((i-4)*30)-10 && mouseX < width/2+825+((i-4)*30)+10) && (mouseY > (height/1.2)+20 && mouseY < (height/1.2)+40)){
          if(mouseButton == LEFT){
            lineDefaultButton = false;
            if(lineGraphButton[i-1] == true){
              lineGraphButton[i-1] = false;
            }else{
              lineGraphButton[i-1] = true;
            }
          }
        }
      }
    }
    if((mouseX > width/2+915-10 && mouseX < width/2+915+10) && (mouseY > (height/1.075)-25) && mouseY < (height/1.075)+25){
      if(mouseButton == LEFT){
        if(okFilterButton == false){
          okFilterButton = true;
        }
      }
    }
  }
  ////-------------------Increase-Decrease Filter-------------------
  if(expandGraphButton){
    if((mouseX > 870-10 && mouseX < 870+10) && (mouseY > (height/1.105)-12.5) && mouseY < (height/1.105)+12.5){
      if(mouseButton == LEFT && statistics[0].filterMin>0){
        statistics[0].filterMin -= 1;
      }
    }
    if((mouseX > 930-10 && mouseX < 930+10) && (mouseY > (height/1.105)-12.5) && mouseY < (height/1.105)+12.5){
      if(mouseButton == LEFT && statistics[0].filterMin<23){
        statistics[0].filterMin += 1;
      }
    }
    //
    if((mouseX > 870-10 && mouseX < 870+10) && (mouseY > (height/1.055)-12.5) && mouseY < (height/1.055)+12.5){
      if(mouseButton == LEFT && statistics[0].filterMax>0){
        statistics[0].filterMax -= 1;
      }
    }
    if((mouseX > 930-10 && mouseX < 930+10) && (mouseY > (height/1.055)-12.5) && mouseY < (height/1.055)+12.5){
      if(mouseButton == LEFT && statistics[0].filterMax<23){
        statistics[0].filterMax += 1;
      }
    }
    
  }
  if(mapScreenWidth/mapScreenHeight > 1.5){
    if((mouseX > width/2+820-10 && mouseX < width/2+820+10) && (mouseY > (height/1.105)-12.5) && mouseY < (height/1.105)+12.5){
      if(mouseButton == LEFT && statistics[0].filterMin>0){
        statistics[0].filterMin -= 1;
      }
    }
    if((mouseX > width/2+880-10 && mouseX < width/2+880+10) && (mouseY > (height/1.105)-12.5) && mouseY < (height/1.105)+12.5){
      if(mouseButton == LEFT && statistics[0].filterMin<23){
        statistics[0].filterMin += 1;
      }
    }
    //
    if((mouseX > width/2+820-10 && mouseX < width/2+820+10) && (mouseY > (height/1.055)-12.5) && mouseY < (height/1.055)+12.5){
      if(mouseButton == LEFT && statistics[0].filterMax>0){
        statistics[0].filterMax -= 1;
      }
    }
    if((mouseX > width/2+880-10 && mouseX < width/2+880+10) && (mouseY > (height/1.055)-12.5) && mouseY < (height/1.055)+12.5){
      if(mouseButton == LEFT && statistics[0].filterMax<23){
        statistics[0].filterMax += 1;
      }
    }
  }
  
  ////-------------------Helping Menu Button-------------------
  if(helpButton){
    if(mapScreenWidth/mapScreenHeight < 1.5){
      if((mouseX>100 && mouseX<150) && (mouseY>(height*0.075)-25) && mouseY<(height*0.075)+25){
        if(mouseButton == LEFT){
          helpingMenuButton = !helpingMenuButton;
        }
      }
    }
  }
}

//--------------------------------------
//--------------------------------------Components Function--------------------------------------
//--------------------------------------

void keyPressed(){
  if(keyCode == UP || keyCode == DOWN){
    Xrotate = true;
  }
  if(keyCode == RIGHT || keyCode == LEFT){
    Yrotate = true;
  }
}

void keyReleased(){
  Xrotate = false;
  Yrotate = false;
  warningRotate = "";
}

void textPart(){
    textFont(f1, 30);
    fill(175);
    text("Scooter O-D flows (Calgary, Canada)", width-450, 30);
    
    textFont(f1, 25);
    fill(255,0,0);
    text(warningRotate, width-310, 60);
    
    if(showInteractButton){
      textFont(f1, 35);
      fill(255,200);
      String ml = nf(times.clocks, 2);
      String sc = nf(times.second, 2);
      String mn = nf(times.minute, 2);
      text(mn+":", interacts.mapRight-150, 384);
      text(sc+":", interacts.mapRight-100, 384);
      text(ml, interacts.mapRight-50, 384);
      
      textFont(f1, 20);
      fill(255,200);
      text(times.date + " July 2019", interacts.mapRight-110,408.51063);
      
      fill(statistics[0].lineColor[times.start_date]);
      text(times.datesWeek[times.start_date], interacts.mapRight-150,408.51063);
      
      textFont(f1, 20);
      fill(255);
      text("Building Display", 20,(height/1.4)-139);
      text("Light Option", 20,(height/1.4)-91);
      text("Reset Hour", 20, (height/1.4)-43);
      text("Reset View", 20,(height/1.4)+5);
      
      textFont(f1, 15);
      fill(255);
      text("Pick-Up", 65,340);
      text("Drop-Off", 165,340);
      
      if((mouseX > 200-37.5 && mouseX < 200+37.5) && (mouseY > (height/1.5)-15 && mouseY <(height/1.5)+15)){
        if(mousePressed && (mouseButton == LEFT)){
          textFont(f1, 25);
          fill(255);
          text("Hour Reset", interacts.mapRight-20, height/2.75);
        }
      }
      if((mouseX > 200-37.5 && mouseX < 200+37.5) && (mouseY > (height/1.4)-15 && mouseY <(height/1.4)+15)){
        if(mousePressed && (mouseButton == LEFT)){
          textFont(f1, 25);
          fill(255);
          text("View Reset", interacts.mapRight-20, height/1.4);
        }
      }
      for(int i=0; i<selectDateButton.length; i++){
        if((mouseX > 27.5+(i*30) && mouseX < 52.5+(i*30)) && (mouseY > (436.363636)-12.5) && mouseY < (436.363636)+12.5){
          textFont(f1, 25);
          fill(50);
          text(15+i, mouseX+10, mouseY+10);
        }
      }
    }
    
    if(showGraphButton){
      textFont(font_date, 15);
      fill(255,300);
      text("15 July 2019: ", 20, (height/1.04)-150);
      text("16 July 2019: ", 20, (height/1.04)-125);
      text("17 July 2019: ", 20, (height/1.04)-100);
      text("18 July 2019: ", 20, (height/1.04)-75);
      text("19 July 2019: ", 20, (height/1.04)-50);
      text("20 July 2019: ", 20, (height/1.04)-25);
      text("21 July 2019: ", 20, height/1.04);
      
      fill(50,200,50,300);
      text(dateCount[0], 100, (height/1.04)-150);
      text(dateCount[1], 100, (height/1.04)-125);
      text(dateCount[2], 100, (height/1.04)-100);
      text(dateCount[3], 100, (height/1.04)-75);
      text(dateCount[4], 100, (height/1.04)-50);
      text(dateCount[5], 100, (height/1.04)-25);
      text(dateCount[6], 100, height/1.04);
      
      fill(255,300);
      textFont(font_date, 20); //250 318 412
      text("Trips at        o'clocks: ", 20, height/1.285);
      
      textFont(font_date, 25);
      fill(255,75,75);
      text(times.minute, 87.5, height/1.28);
      text(barHeights[times.minute], 190, height/1.28);

    }
}

void textpartExpand(){
  if((mouseX > width-97.5 && mouseX < width-22.5) && (mouseY > (height/1.275)-15 && mouseY <(height/1.275)+15)){
    if(!expandGraphButton && mapScreenWidth/mapScreenHeight <1.5){
      textFont(f1, 20);
      fill(255);
      text("Expand Graph", mouseX-50, mouseY);
    }
  }
  if(expandGraphButton){
    
    //-------------------Second Graph Part-------------------!
    
    if((mouseX > width-97.5 && mouseX < width-22.5) && (mouseY > (height/1.275)-15 && mouseY <(height/1.275)+15)){
      if(expandGraphButton && mapScreenWidth/mapScreenHeight <1.5){
        textFont(f1, 20);
        fill(255);
        text("Minimize", mouseX-35, mouseY);
      }
    }
  }
}

void quitMenuButton(){
  //-------------------Button Part-------------------
  pushMatrix();
      stroke(100,0,0,50);
      //quit checking
      if((mouseX > 167.5 && mouseX <242.5) && (mouseY > (height*0.075)-25 && mouseY <(height*0.075)+25)){
            quit = "QUIT";
            quitColor = color(250,0,0);
          if(mousePressed && (mouseButton == LEFT)){
            quitButton = true;
            quitProgram();
          }
      }else{
          quitColor = color(150,0,0);
          quit = "";
      }
      
      rectMode(CENTER);
      fill(quitColor);
      rect(205,height*0.075,75,50,5,5,5,5); // --> Quit Button
      pushMatrix();
        fill(255);
        textFont(f1, 35);
        text(quit, mouseX-20, mouseY-20);
      popMatrix();
   popMatrix();
}

void pick_dropButton(){
  if(showInteractButton){
    pushMatrix();
    stroke(0,100);
    //pick-up toggle
      if((mouseX > 40 && mouseX < 60) && (mouseY > interacts.mapBottom-5 && mouseY <interacts.mapBottom+35)){
        if(pickupButton){
          pickupColor = color(255,0,0);
        }else{
          pickupColor = color(125);
        }
      }
    
    rectMode(CENTER);
    fill(pickupColor);
    rect(50,interacts.mapBottom+15,20,20,5,5,5,5);
    
    //-------------------Drop-off Toggle-------------------
    if((mouseX > 140 && mouseX < 160) && (mouseY > interacts.mapBottom-5 && mouseY <interacts.mapBottom+35)){
      if(dropoffButton){
        dropoffColor = color(255);
      }else{
        dropoffColor = color(125);
      }
    }
    
    rectMode(CENTER);
    fill(dropoffColor);
    rect(150,interacts.mapBottom+15,20,20,5,5,5,5);
    
    popMatrix();
  }
}

void pauseButton(){
  pushMatrix();
  if(showInteractButton){
    //pause checking
    if((mouseX > 30 && mouseX < 90) && (mouseY > (384)-25 && mouseY <(384)+25)){
      backgroundPause = loadImage(url_pauseButton2);
    }else{
      backgroundPause = loadImage(url_pauseButton1);
    }
    
    noStroke();
    beginShape(QUADS);
    texture(backgroundPause);
    vertex(30,(384)-25,0,0);
    vertex(90,(384)-25,50,0);
    vertex(90,(384)+25,50,50);
    vertex(30,(384)+25,0,50);
    endShape();
  }

  popMatrix();
}

void showBuildingButton(){
  if(showInteractButton){
  pushMatrix();
      stroke(200,100);
      //building checking
      if(showBuildingButton){
        showBuildingColor = color(#40A0D8);
      }
      if((mouseX > 200-37.5 && mouseX < 200+37.5) && (mouseY > (height/1.4)-159 && mouseY <(height/1.4)-129)){
        if(!showBuildingButton){
          showBuildingColor = color(#0F4869);
        }else{
          showBuildingColor = color(#40A0D8);
        }
      }
      
      rectMode(CENTER);
      fill(showBuildingColor);
      rect(200,(height/1.4)-144,75,30,5,5,5,5);
   popMatrix();
   }
}

void lightingButton(){
  if(showInteractButton){
  pushMatrix();
    stroke(200,100);
    //origin point checking
    if((mouseX > 200-37.5 && mouseX < 200+37.5) && (mouseY > (height/1.4)-111 && mouseY <(height/1.4)-81)){
      lightingColor = color(#40A0D8);
    }else{
        lightingColor = color(#0F4869);
    }
    if(lightingButton){
      lightingColor = color(#40A0D8);
    }else{
      lightingColor = color(#0F4869);
    }
    
    rectMode(CENTER);
    fill(lightingColor);
    rect(200,(height/1.4)-96,75,30,5,5,5,5);
 popMatrix();
 }
}

void resetHourButton(){
  if(showInteractButton){
  pushMatrix();
      stroke(200,100);
      //reset hour checking
      if((mouseX > 200-37.5 && mouseX < 200+37.5) && (mouseY > (height/1.4)-63 && mouseY <(height/1.4)-33)){
        resetHourColor = color(#40A0D8);
      }else{
          resetHourColor = color(#0F4869);
      }
      
      rectMode(CENTER);
      fill(resetHourColor);
      rect(200,(height/1.4)-48,75,30,5,5,5,5);
   popMatrix();
   }
}

void resetCameraButton(){
  if(showInteractButton){
    pushMatrix();
      stroke(200,100);
      //reset camara checking
      if((mouseX > 200-37.5 && mouseX < 200+37.5) && (mouseY > (height/1.4)-15 && mouseY <(height/1.4)+15)){
        resetCameraColor = color(#40A0D8);
      }else{
          resetCameraColor = color(#0F4869);
      }
      
      rectMode(CENTER);
      fill(resetCameraColor);
      rect(200,height/1.4,75,30,5,5,5,5);
   popMatrix();
  }
}

void showInteractButton(){
  pushMatrix();
      stroke(200,50);
      //Interact checking
      if((mouseX > 95 && mouseX <145) && (mouseY > (height*0.075)-25 && mouseY <(height*0.075)+25)){
        if(showInteractButton){
          showI = "Hide Interact";
        }else{
          showI = "Show Interact";
        }
        showInteractColor = color(175);
      }else{
          showInteractColor = color(75);
          showI = "";
      }
      
      rectMode(CENTER);
      fill(showInteractColor);
      rect(120,height*0.075,50,50,5,5,5,5); // --> Show/Hide Button
      pushMatrix();
        fill(255);
        text(showI, mouseX-20, mouseY-20);
      popMatrix();
   popMatrix();
}

void showGraphButton(){
  pushMatrix();
      stroke(200,50);
      //graph checking
      if((mouseX > 25 && mouseX <75) && (mouseY > (height*0.075)-25 && mouseY <(height*0.075)+25)){
        if(showGraphButton){
          showG = "Hide Graph";
        }else{
          showG = "Show Graph";
        }
        showGraphColor = color(175);
      }else{
          showGraphColor = color(75);
          showG = "";
      }
      
      rectMode(CENTER);
      fill(showGraphColor);
      rect(50,height*0.075,50,50,5,5,5,5); // --> Show/Hide Button
      pushMatrix();
        fill(255);
        text(showG, mouseX-20, mouseY-20);
      popMatrix();
   popMatrix();
}

void quitProgram(){
  if(quitButton){
    viewX = 0;
    viewY = 0;
    scale = 0.75;
    translateX = 0.0;
    translateY = 0.0;
    times.minute =0;
    times.second =0;
    startButton = false;
    helpButton = false;
    barHeights = new int[24];
    increasing = new int[24];
    showBuildingButton = true;
    //quitButton = false;
  }
}

void printAllFileNames() {
  int index =0;
  File dataFolder = new File(savePath("/data")); //return the path to the data folder
  url_output = new String[dataFolder.listFiles().length/2];
  url_timeOutput = new String[dataFolder.listFiles().length/2];
  
  File[] files = dataFolder.listFiles();
  for(int i = 0; i < files.length; i++) {
    if(files[i].getName().endsWith("output.txt")) {
      url_output[index] = files[i].getName();
      index += 1;
    }
  }
  index=0;
  for(int i = 0; i < files.length; i++) {
    if(files[i].getName().endsWith("timeOutput.txt")) {
      url_timeOutput[index] = files[i].getName();
      index += 1;
    }
  }
  for(int i=0; i<url_output.length; i++){
    println(url_output[i], url_timeOutput[i]);
  }
}

void hightlightGraph(){
  if(showGraphButton){    //-------------------For the First Graph-------------------
    if((mouseX > 150 && mouseX < 450) && (mouseY < height/1.0422 && mouseY > height/1.25)){
      analytics[0].graphColor1 = color(255,175);
      textFont(f1, 20);
      fill(255);
      text("AM",420,height/1.255);
      
      textFont(f2, 15);
      fill(255);
      text("Hour",150,height/1.255);
      text("Trips",420,height/1.025);
      
      for(int i=0; i<analytics.length/2; i++){
        if(i <= times.minute){
          pushMatrix();
            translate(150,(height/1.270)-i*13.5);
            fill(50);
            textFont(f2, 15);
            if(mapScreenWidth/mapScreenHeight < 1.5){
              text(barHeights[i], (barHeights[i]*7.5)+10,i+160);
            }else{
              text(barHeights[i], (barHeights[i]*7.5)+10,i+165);
            }
          popMatrix();
        }
      }
      
      textFont(f1, 12.5);
      fill(255);
      for(int i=0; i<(analytics.length/2); i++){
        if(i != times.minute){
          pushMatrix();
          translate(155,(height/1.270)-i*12.5);
          if(mapScreenWidth/mapScreenHeight < 1.5){
            text(i,-20,160-(analytics[i].barHeight*i));
          }else{
            text(i,-20,165-(analytics[i].barHeight*i));
          }
          popMatrix();
        }
      }
    }else{
      analytics[0].graphColor1 = color(150,0);
    }
    if((mouseX > 470 && mouseX < 770) && (mouseY < height/1.0422 && mouseY > height/1.25)){
      if(times.minute > 11){
        analytics[0].graphColor2 = color(255,175);
        textFont(f1, 20);
        fill(50);
        text("PM",740,height/1.255);
        
        textFont(f1, 15);
        fill(255);
        text("Hour",470,height/1.255);
        text("Trips",740,height/1.025);
        
        for(int i=analytics.length/2; i<analytics.length; i++){
          if(i <= times.minute){
            pushMatrix();
              fill(50);
              translate(150,(height/1.270)-(i-12)*13.5);
              textFont(f2, 15);
              if(mapScreenWidth/mapScreenHeight < 1.5){
                text(barHeights[i],320+(barHeights[i]*7.5)+10,(i-12)+160);
              }else{
                text(barHeights[i],320+(barHeights[i]*7.5)+10,(i-12)+165);
              }
            popMatrix();
            
          }
        }
        
        textFont(f2, 12.5);
        fill(255);
        for(int i=(analytics.length/2); i<analytics.length; i++){
          if(i != times.minute){
            pushMatrix();
            translate(475,(height/1.270)-i*12.5);
            if(mapScreenWidth/mapScreenHeight < 1.5){
              text(i,-20,310-(analytics[i].barHeight*i));
            }else{
              text(i,-20,315-(analytics[i].barHeight*i));
            }
            popMatrix();
          }
        }
      }
    }else{
      analytics[0].graphColor2 = color(150,0);
    }
  }
}

void hightlightSecondGraph(){
  if(expandGraphButton){   //-------------------For the Second Graph-------------------
    if(mapScreenWidth/mapScreenHeight < 1.5){
      if((mouseX > 350 && mouseX < 850) && (mouseY < height/1.0422 && mouseY > height/1.275)){
        statistics[0].graphColor = color(175,175);
        
        textFont(f2,12.5);
        fill(255);
        text("Trips",340,height/1.285);
        text("Hour",815,height/1.05);
        for(int i=0; i<24; i++){
          pushMatrix();
          translate(365+i*4,(height/1.023));
          text(i,i*15,0);
          popMatrix();
        }
        for(int i=0; i<7; i++){
          pushMatrix();
          textFont(f2,12.5);
          translate(330,(height/1.045)-i*4.5);
          text(i*5,0,-i*20);
          popMatrix();
        }
        textFont(f2,15);
        fill(50);
        noStroke();
        rect(820, (height/1.275), 850, (height/1.275)+125);
        for(int i=0; i<statistics.length; i++){
          if(lineGraphButton[i]){
            fill(statistics[i].lineColor[i]);
            text(statistics[i].sumDate, 825,(height/1.25)+(i*17.5));
          }
        }
      }else{
        statistics[0].graphColor = color(150,0);
      }
    }
  }if(mapScreenWidth/mapScreenHeight > 1.5){
      if((mouseX > width/2+300 && mouseX < width/2+800) && (mouseY < height/1.0422 && mouseY > height/1.275)){
        statistics[0].graphColor = color(255,175);
        
        textFont(f2,12.5);
        fill(255);
        text("Trips",width/2+290,height/1.285);
        text("Hour",width/2+765,height/1.05);
        for(int i=0; i<24; i++){
          pushMatrix();
          translate(width/2+305+i*4,(height/1.023));
          text(i,i*15,0);
          popMatrix();
        }
        for(int i=0; i<7; i++){
          pushMatrix();
          textFont(f2,12.5);
          translate(width/2+280,(height/1.045)-i*4.5);
          text(i*5,0,-i*20);
          popMatrix();
        }
        textFont(f2,15);
        fill(50);
        noStroke();
        rect(width/2+770, (height/1.275), width/2+800, (height/1.275)+125);
        for(int i=0; i<statistics.length; i++){
          if(lineGraphButton[i]){
            fill(statistics[i].lineColor[i]);
            text(statistics[i].sumDate, width/2+775,(height/1.25)+(i*17.5));
          }
        }
      }else{
        statistics[0].graphColor = color(150,0);
      }
  }
}

void selectDateButton(){
  if(showInteractButton){
    pushMatrix();
    
    int[] dateButton = new int[7];
    noStroke();
    for(int i=0; i<dateButton.length; i++){
      rectMode(CENTER);
      fill(selectDateColor[i]);
      rect(40+(i*30), 436.363636, 25,25);
    }
    interacts.presentDate();
    fill(selectDateColor[times.start_date],25);
    rect(40+(times.start_date*30),436.363636, 25,25);
    popMatrix();
  }
  for(int i=0; i<selectDateButton.length; i++){
    if(selectDateButton[i]){
      times.minute =0;
      times.second =0;
      times.clocks =0;
      barHeights = new int[24];
      increasing = new int[24];
      dateCount[i] = 0;
    }
    selectDateButton[i] = false;
  }
}

void increaseDecreaseDateButton(){
  int low;
  int high;
  if(mapScreenWidth/mapScreenHeight > 1.5){
    low = 500;
    high = 530;
  }else{
    low = 490;
    high = 520;
  }
  
  if(showInteractButton){
    pushMatrix();
    
    if((mouseX > 30 && mouseX < 80) && (mouseY > low && mouseY < high )){
      backgroundDecrease = loadImage(url_decreaseButton2);
    }else{
      backgroundDecrease = loadImage(url_decreaseButton1);
    }
    beginShape(QUADS);
    texture(backgroundDecrease);
    vertex(30,low,0,0);
    vertex(80,low,100,0);
    vertex(80,high,100,50);
    vertex(30,high,0,50);
    endShape();
    
    if((mouseX > 100 && mouseX < 150) && (mouseY > low && mouseY < high )){
      backgroundIncrease = loadImage(url_increaseButton2);
    }else{
      backgroundIncrease = loadImage(url_increaseButton1);
    }
    
    beginShape(QUADS);
    texture(backgroundIncrease);
    vertex(100,low,0,0);
    vertex(150,low,100,0);
    vertex(150,high,100,50);
    vertex(100,high,0,50);
    endShape();

    popMatrix();
  }
}

void ampmButton(){
  int low;
  int high;
  if(mapScreenWidth/mapScreenHeight > 1.5){
    low = 500;
    high = 530;
  }else{
    low = 490;
    high = 520;
  }
  if(showInteractButton){
    pushMatrix();
    if((mouseX > 175 && mouseX < 225) && (mouseY > low && mouseY < high )){
      if(times.minute < 12){
        backgroundAMPM = loadImage(url_amButton1);
      }else{
        backgroundAMPM = loadImage(url_pmButton1);
      }
    }else{
      if(times.minute <12){
        backgroundAMPM = loadImage(url_amButton2);
      }else{
        backgroundAMPM = loadImage(url_pmButton2);
      }
    }
    
    beginShape(QUADS);
    texture(backgroundAMPM);
    vertex(175,low,0,0);
    vertex(225,low,75,0);
    vertex(225,high,75,50);
    vertex(175,high,0,50);
    endShape();
    
    popMatrix();
  }
}

void expandGraph(){
  if(showGraphButton && mapScreenWidth/mapScreenHeight < 1.5){
    pushMatrix();
      noStroke();
      //expand graph checking
      if((mouseX > width-97.5 && mouseX < width-22.5) && (mouseY > (height/1.275)-15 && mouseY <(height/1.275)+15)){
        if(expandGraphButton){
          expandGraphColor = color(200);
        }else{
          expandGraphColor = color(200);
        }
      }else{
          expandGraphColor = color(150);
      }
      rectMode(CENTER);
      fill(expandGraphColor);
      rect(width-60,height/1.275,75,30);
   popMatrix();
  }
  if(mapScreenWidth/mapScreenHeight > 1.5){
    expandGraphButton = false;
  }
}

void statisticsData(){
  if(expandGraphButton || mapScreenWidth/mapScreenHeight > 1.5){
    for(int i=0; i<filterData.length; i++){
      if(statistics[filterData[i]].min <= stat_min){
        stat_min = statistics[filterData[i]].min;
      }
      if(statistics[filterData[i]].max > stat_max){
        stat_max = statistics[filterData[i]].max;
      }
      if(statistics[filterData[i]].dateMin <= stat_dateMin){
        stat_dateMin = statistics[filterData[i]].dateMin;
      }
      if(statistics[filterData[i]].dateMax > stat_dateMax){
        stat_dateMax = statistics[filterData[i]].dateMax;
      }
   
      avgWeek += statistics[filterData[i]].sumDate;
      sumWeek += statistics[filterData[i]].dataLine.length;
      
      avgDate += statistics[filterData[i]].avgDate;
      
    }
    
    if(filterData.length != 0){
      avgDate = avgDate/filterData.length;
      avgWeek = avgWeek/filterData.length;
    }
  }
}

void textStatisticsData(){
  if(showGraphButton){
    if(expandGraphButton || mapScreenWidth/mapScreenHeight > 1.5){
      if(mapScreenWidth/mapScreenHeight < 1.5){
          textFont(f2,17.5);
          fill(255,300);
          text("The Most Trips per Hour: ", 20, height/1.275);
          text("The Least Trips per Hour: ", 20, height/1.275+25);
          text("The Most Trips per Day: ", 20, height/1.275+65);
          text("The Least Trips per Day: ", 20, height/1.275+90);
          text("Average Trips per Hour: ", 20, height/1.275+130);
          text("Average Trips per Day: ", 20, height/1.275+155);
          
          text(stat_max, 200, height/1.275);
          text(stat_dateMax, 200, height/1.275+65);
          
          if(filterData.length > 0){
            text(stat_min, 200, height/1.275+25);
            text(stat_dateMin, 200, height/1.275+90);
          }else{
            text(0, 200, height/1.275+25);
            text(0, 200, height/1.275+90);
          }
          
          text(avgDate, 200, height/1.275+130);
          text(avgWeek, 200, height/1.275+155);
          
        }else{
          textFont(f2,17.5);
          fill(255,300);
          text("The Most Trips per Hour: ", width/2-100, height/1.275);
          text("The Least Trips per Hour: ", width/2-100, height/1.275+25);
          text("The Most Trips per Day: ", width/2-100, height/1.275+65);
          text("The Least Trips per Day: ", width/2-100, height/1.275+90);
          text("Average Trips per Hour: ", width/2-100, height/1.275+130);
          text("Average Trips per Day: ", width/2-100, height/1.275+155);
          
          text(stat_max, width/2+80, height/1.275);
          text(stat_dateMax, width/2+80, height/1.275+65);
          
          if(filterData.length > 0){
            text(stat_min, width/2+80, height/1.275+25);
            text(stat_dateMin, width/2+80, height/1.275+90);
          }else{
            text(0, width/2+80, height/1.275+25);
            text(0, width/2+80, height/1.275+90);
          }
          
          text(avgDate, width/2+80, height/1.275+130);
          text(avgWeek, width/2+80, height/1.275+155);
        }
    }
  }
}

void resetZero(){
  stat_max =0;
  stat_dateMax =0;
  sumDate=0;
  sumWeek=0;
  avgDate =0;
  avgWeek =0;
  filterData = new int[0];
}

void daylight(){
  if(lightingButton){
    lighting = times.minute*20;
    pushMatrix();
      noStroke();
      if(times.minute <= 12){
        directionalLight(250, 250, 250, 12-times.minute, 2, -10);
      }else{
        directionalLight(250, 250, 250, times.minute-13, 2, -10);
      }
      //translate(width/2, 100, 0);
      //sphere(60);
    popMatrix();
  }
}

void helpingMenuPanel(){
  noStroke();
  if(mapScreenWidth/mapScreenHeight < 1.5){
    if((mouseX>100 && mouseX<150) && (mouseY>(height*0.075)-25) && mouseY<(height*0.075)+25){
      helpingMenuColor = color(175);
      help = "Swith Page";
    }else{
      helpingMenuColor = color(250);
      help = "";
    }
    fill(helpingMenuColor);
    rect(125,height*0.075,50,50,5,5,5,5); // ---HelpingMenu1
    
    fill(255);
    textFont(f1, 20);
    text(help, mouseX, mouseY-20);
    
  }
  fill(255,150);
  textFont(f2, 17.5);
  text("Powered by Processing 3.0", width-200, 40);
  text("Featuring with Google Maps API", width-232.5, 60);
  text("Map Theme by Mapbox", width-170, 80);
  text("Project Version: 0.28.0", width-170, 100);
}

void helpingMenu1(){
  if(mapScreenWidth/mapScreenHeight < 1.5){
    fill(255);
    rectMode(LEFT);
    rect(0,135, width, height-35);
    
    beginShape(QUAD);
    texture(backgroundHelping_1);
    vertex(100,150, 0,0);
    vertex(width-100,150,1000,0);
    vertex(width-100,height-50,1000,672);
    vertex(100,height-50,0,672);
    endShape();
  }else{
    fill(255);
    rectMode(LEFT);
    rect(0,135, width, height-35);
    
    beginShape(QUAD);
    texture(backgroundHelping_1);
    vertex(100,150, 0,0);
    vertex((width*0.5102040)-100,150,1000,0);
    vertex((width*0.5102040)-100,height-50,1000,672);
    vertex(100,height-50,0,672);
    endShape();
  }
}

void helpingMenu2(){
  if(mapScreenWidth/mapScreenHeight < 1.5){
    fill(255);
    rectMode(LEFT);
    rect(0,135, width, height-35);
    
    beginShape(QUAD);
    texture(backgroundHelping_2);
    vertex(100,150, 0,0);
    vertex(width-100,150,967,0);
    vertex(width-100,(height/2)-50,967,400);
    vertex(100,(height/2)-50,0,400);
    endShape();
    
    beginShape(QUAD);
    texture(backgroundHelping_3);
    vertex(100,(height/2)+150, 0,0);
    vertex(width-100,(height/2)+150,967,0);
    vertex(width-100,height-50,967,400);
    vertex(100,height-50,0,400);
    endShape();
  }else{
    
    beginShape(QUAD);
    texture(backgroundHelping_2);
    vertex((width/2)+100,150, 0,0);
    vertex(width-100,150,967,0);
    vertex(width-100,(height/2)-50,967,400);
    vertex((width/2)+100,(height/2)-50,0,400);
    endShape();
    
    beginShape(QUAD);
    texture(backgroundHelping_3);
    vertex((width/2)+100,(height/2)+150, 0,0);
    vertex(width-100,(height/2)+150,967,0);
    vertex(width-100,height-50,967,400);
    vertex((width/2)+100,height-50,0,400);
    endShape();
    
  }
}

void rotateLeftRight(){
  if(Yrotate){
    if((scale <=10 && viewX >= -50) && (scale <=10 && viewX <= 50)){
      if(keyCode == RIGHT){
        viewY += 10;
      }else if(keyCode == LEFT){
        viewY -= 10;
      }
      warningRotate = "";
    }else{
      if(keyCode == RIGHT || keyCode == LEFT){
        warningRotate = "Y-Axis Error! Can't Rotate Map.";
      }
    }
    rotateY(viewY*TWO_PI/width); 
  }else if(!Yrotate){
    rotateY(viewY*TWO_PI/width);
  }
  if(mapScreenWidth/mapScreenHeight < 1.5){
    if(viewY < -1000){ viewY = 0;}
    if(viewY >  1000){ viewY = 0;}
  }else{
    if(viewY < -1920){ viewY = 0;}
    if(viewY >  1920){ viewY = 0;}
  }
}

void rotateTopBot(){
  if(Xrotate){
    if((scale <=10 && viewY <= 250) || (scale <=10 && viewX >= 750)){
      viewX = constrain(viewX,-150,50);
      if(keyCode == UP){
        viewX += 10;
      }else if(keyCode == DOWN){
        viewX -= 10;
      }
      warningRotate = "";
    }if(viewY > 250 && viewY < 750){
      if(keyCode == UP || keyCode == DOWN){
        warningRotate = "X-Axis Error! Can't Rotate Map.";
      }
    }
    rotateX(viewX*TWO_PI/height);
  }else if(!Xrotate){
    rotateX(viewX*TWO_PI/height);
  }
}

void drawPattern() {
  for (int iter = 0; iter < width; iter+=5) {
    float h = random(height-150,height);
    stroke(255,0, h/(500+(height-100)) * 255, 100);
    line(iter,height,iter,h);
  }
}
