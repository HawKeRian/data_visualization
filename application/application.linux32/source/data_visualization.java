import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class data_visualization extends PApplet {

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

float mapGeoLeft   = -114.257275f;       // Longitude degree west
float mapGeoRight  = -113.865379f;       // Longitude degree east
float mapGeoTop    = 51.183097f;       // Latitude degree north
float mapGeoBottom = 50.875688f;          // Latitude degree south

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

float scale =0.75f;
float viewX=0;
float viewY=0;
float camX=0;
float camY=0;
float translateX=0;
float translateY=0;
float bgLight = 52.5f;
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

int quitColor = color(255,0,0);
int showBuildingColor = color(0xff40A0D8);
int lightingColor = color(255,0,0);
int showDestColor = color(255,0,0);
int showGraphColor = color(255,0,0);
int showInteractColor = color(255,0,0);
int pickupColor = color(255,0,0);
int dropoffColor = color(255);
int barColor = color(0,7,0);
int resetHourColor = color(255,0,0);
int resetCameraColor = color(255,0,0);
int[] selectDateColor = new int[7];
int expandGraphColor = color(255,0,0);
int helpingMenuColor = color(200);

public void setup(){
  
  
  
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

public void draw(){
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
      if((mouseX > (width*0.5f)-75 && mouseX <(width*0.5f)+75) && (mouseY > (height/2)-35 && mouseY <(height/2)+35)){
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
      if((mouseX > (width*0.5f)-75 && mouseX <(width*0.5f)+75) && (mouseY > (height/1.7f)-35 && mouseY <(height/1.7f)+35)){
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
      vertex((width/2)-75,(height/1.7f)-35,0,0);
      vertex((width/2)+75,(height/1.7f)-35,150,0);
      vertex((width/2)+75,(height/1.7f)+35,150,68);
      vertex((width/2)-75,(height/1.7f)+35,0,68);
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
      scale = constrain(scale,0.5f,10);
      translate(translateX, translateY);
      scale(scale,scale,scale/2);
      
      //-------------------3D Mapping-------------------;
      if(mapScreenWidth/mapScreenHeight < 1.5f){
        mapDiameter = sqrt(pow(mapScreenWidth,2) + pow(mapScreenHeight,2)); //1,386.2178
      }else{
        mapDiameter = sqrt(pow(mapScreenWidth*0.543077f,2) + pow(mapScreenHeight,2));
      }
      
      translate((width/2.0f-camX)+interacts.mapRight, (height/2.0f-camY), -mapDiameter/1.6f);
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
      
      if(mapScreenWidth/mapScreenHeight < 1.5f){
        for(int i=0; i<(analytics.length/2); i++){
          if(increasing[i]<barHeights[i]*7.5f){
            increasing[i] += 1;
          }
          pushMatrix();
            translate(155,(height/1.270f)-i*13.5f);
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
              translate(155,(height/1.270f)-(i-12)*13.5f);
              fill(barColor,150);
              noStroke();
              rectMode(LEFT);
              rect(320,(i-12)+150,320+increasing[i],(i-12)+160);
            popMatrix();
          }
        }
      }else{
        for(int i=0; i<(analytics.length/2); i++){
          if(increasing[i]<barHeights[i]*7.25f){
            increasing[i] += 1;
          }
          pushMatrix();
            translate(155,(height/1.270f)-i*13.5f);
            fill(barColor,150);
            noStroke();
            rectMode(LEFT);
            rect(0,i+155,increasing[i],i+165);
          popMatrix();
        }
        if(times.minute>=12){
          for(int i=analytics.length/2; i<analytics.length; i++){
            if(increasing[i]<barHeights[i]*7.25f){
              increasing[i] += 1;
            }
            pushMatrix();
              translate(155,(height/1.270f)-(i-12)*13.5f);
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
    
    if(mapScreenWidth/mapScreenHeight < 1.5f){
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

public void drawMap(){
  noStroke();
  float drawWidth;
  if(mapScreenWidth/mapScreenHeight > 1.5f){
    drawWidth = mapScreenWidth*0.543077f;
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
  vertex(drawWidth/2.5f,-5,-mapScreenHeight/2,0,0);
  vertex(drawWidth/2,-5,-mapScreenHeight/2,504,0);
  vertex(drawWidth/2,-5,-mapScreenHeight/3,504,666);
  vertex(drawWidth/2.5f,-5,-mapScreenHeight/3,0,666);
  endShape();
}

public void mouseWheel(MouseEvent e){
  translateX -= mouseX;
  translateY -= mouseY;
  float delta = e.getCount();
  if(delta >0 && scale != 10){
    delta = 1.1f;
  }else if(delta < 0 && scale != 0.5f){
    delta = 1.0f/1.05f;
  }else{
    delta = 1.0f;
  }
  println(scale);
  translateX *= delta;
  translateY *= delta;
  translateX += mouseX;
  translateY += mouseY;
  scale *= delta;
}

public void mouseDragged(){
  if(mouseX > 250 && mouseY < height/1.322f){
    if(mouseButton == LEFT){
    camX += (pmouseX - mouseX);
    camY += (pmouseY - mouseY);
  }
  translateX += mouseX - pmouseX;
  translateY += mouseY - pmouseY;
  }
}

public void mouseClicked(){
  int low;
  int high;
  if(mapScreenWidth/mapScreenHeight > 1.5f){
    low = 500;
    high = 530;
  }else{
    low = 490;
    high = 520;
  }
  
  //-------------------Interaction Part-------------------
  if(showInteractButton){
    //Building BUtton
    if((mouseX > 200-37.5f && mouseX < 200+37.5f) && (mouseY > (height/1.4f)-159 && mouseY <(height/1.4f)-129)){
      if(mouseButton == LEFT){
        if(showBuildingButton == true) {
          showBuildingButton = false;
        }else{
          showBuildingButton = true;
        }
      }
    }
    
    //-------------------Light Option Button-------------------
    if((mouseX > 200-37.5f && mouseX < 200+37.5f) && (mouseY > (height/1.4f)-111 && mouseY <(height/1.4f)-81)){
      if(mouseButton == LEFT){
        if(lightingButton == true) {
          lightingButton = false;
        }else{
          lightingButton = true;
        }
      }
    }
    
    //-------------------Reset Hour Button-------------------
    if((mouseX > 200-37.5f && mouseX < 200+37.5f) && (mouseY > (height/1.4f)-63 && mouseY <(height/1.4f)-33)){
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
    if((mouseX > 200-37.5f && mouseX < 200+37.5f) && (mouseY > (height/1.4f)-15 && mouseY <(height/1.4f)+15)){
      if(mouseButton == LEFT){
        viewX = 0;
        viewY = 0;
        scale = 0.75f;
        translateX = 0.0f;
        translateY = 0.0f;
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
      if(mapScreenWidth/mapScreenHeight < 1.5f){
        if((mouseX > 27.5f+(i*30) && mouseX < 52.5f+(i*30)) && (mouseY > (436.363636f)-12.5f) && mouseY < (436.363636f)+12.5f){
          if(mouseButton == LEFT && times.start_date != i){
            selectDateButton[i] = true;
            times.start_date = 0;
          }
        }
      }else{
        if((mouseX > 27.5f+(i*30) && mouseX < 52.5f+(i*30)) && (mouseY > (436.363636f)-12.5f) && mouseY < (436.363636f)+12.5f){
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
  if((mouseX > 25 && mouseX <75) && (mouseY > (height*0.075f)-25 && mouseY <(height*0.075f)+25)){
    if(mouseButton == LEFT){
      if(showGraphButton == true) {
        showGraphButton = false;
      }else{
        showGraphButton = true;
      }
    }
  }
  
  //-------------------ShowInteract Button-------------------
  if((mouseX > 95 && mouseX <145) && (mouseY > (height*0.075f)-25 && mouseY <(height*0.075f)+25)){
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
    if((mouseX > width-97.5f && mouseX < width-22.5f) && (mouseY > (height/1.275f)-15 && mouseY <(height/1.275f)+15)){
      if(mouseButton == LEFT && mapScreenWidth/mapScreenHeight < 1.5f){
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
    if((mouseX > 875-10 && mouseX < 875+10) && (mouseY > (height/1.2f)-10 && mouseY < (height/1.2f)+10)){
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
        if((mouseX > 875+(i*30)-10 && mouseX < 875+(i*30)+10) && (mouseY > (height/1.2f)-10 && mouseY < (height/1.2f)+10)){
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
        if((mouseX > 875+((i-4)*30)-10 && mouseX < 875+((i-4)*30)+10) && (mouseY > (height/1.2f)+20 && mouseY < (height/1.2f)+40)){
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
    if((mouseX > 965-15 && mouseX < 965+15) && (mouseY > (height/1.075f)-25) && mouseY < (height/1.075f)+25){
      if(mouseButton == LEFT){
        if(okFilterButton == false){
          okFilterButton = true;
        }
      }
    }
    
  }
  if(mapScreenWidth/mapScreenHeight > 1.5f){
    if((mouseX > width/2+825-10 && mouseX <  width/2+825+10) && (mouseY > (height/1.2f)-10 && mouseY < (height/1.2f)+10)){
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
        if((mouseX > width/2+825+(i*30)-10 && mouseX < width/2+825+(i*30)+10) && (mouseY > (height/1.2f)-10 && mouseY < (height/1.2f)+10)){
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
        if((mouseX > width/2+825+((i-4)*30)-10 && mouseX < width/2+825+((i-4)*30)+10) && (mouseY > (height/1.2f)+20 && mouseY < (height/1.2f)+40)){
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
    if((mouseX > width/2+915-10 && mouseX < width/2+915+10) && (mouseY > (height/1.075f)-25) && mouseY < (height/1.075f)+25){
      if(mouseButton == LEFT){
        if(okFilterButton == false){
          okFilterButton = true;
        }
      }
    }
  }
  ////-------------------Increase-Decrease Filter-------------------
  if(expandGraphButton){
    if((mouseX > 870-10 && mouseX < 870+10) && (mouseY > (height/1.105f)-12.5f) && mouseY < (height/1.105f)+12.5f){
      if(mouseButton == LEFT && statistics[0].filterMin>0){
        statistics[0].filterMin -= 1;
      }
    }
    if((mouseX > 930-10 && mouseX < 930+10) && (mouseY > (height/1.105f)-12.5f) && mouseY < (height/1.105f)+12.5f){
      if(mouseButton == LEFT && statistics[0].filterMin<23){
        statistics[0].filterMin += 1;
      }
    }
    //
    if((mouseX > 870-10 && mouseX < 870+10) && (mouseY > (height/1.055f)-12.5f) && mouseY < (height/1.055f)+12.5f){
      if(mouseButton == LEFT && statistics[0].filterMax>0){
        statistics[0].filterMax -= 1;
      }
    }
    if((mouseX > 930-10 && mouseX < 930+10) && (mouseY > (height/1.055f)-12.5f) && mouseY < (height/1.055f)+12.5f){
      if(mouseButton == LEFT && statistics[0].filterMax<23){
        statistics[0].filterMax += 1;
      }
    }
    
  }
  if(mapScreenWidth/mapScreenHeight > 1.5f){
    if((mouseX > width/2+820-10 && mouseX < width/2+820+10) && (mouseY > (height/1.105f)-12.5f) && mouseY < (height/1.105f)+12.5f){
      if(mouseButton == LEFT && statistics[0].filterMin>0){
        statistics[0].filterMin -= 1;
      }
    }
    if((mouseX > width/2+880-10 && mouseX < width/2+880+10) && (mouseY > (height/1.105f)-12.5f) && mouseY < (height/1.105f)+12.5f){
      if(mouseButton == LEFT && statistics[0].filterMin<23){
        statistics[0].filterMin += 1;
      }
    }
    //
    if((mouseX > width/2+820-10 && mouseX < width/2+820+10) && (mouseY > (height/1.055f)-12.5f) && mouseY < (height/1.055f)+12.5f){
      if(mouseButton == LEFT && statistics[0].filterMax>0){
        statistics[0].filterMax -= 1;
      }
    }
    if((mouseX > width/2+880-10 && mouseX < width/2+880+10) && (mouseY > (height/1.055f)-12.5f) && mouseY < (height/1.055f)+12.5f){
      if(mouseButton == LEFT && statistics[0].filterMax<23){
        statistics[0].filterMax += 1;
      }
    }
  }
  
  ////-------------------Helping Menu Button-------------------
  if(helpButton){
    if(mapScreenWidth/mapScreenHeight < 1.5f){
      if((mouseX>100 && mouseX<150) && (mouseY>(height*0.075f)-25) && mouseY<(height*0.075f)+25){
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

public void keyPressed(){
  if(keyCode == UP || keyCode == DOWN){
    Xrotate = true;
  }
  if(keyCode == RIGHT || keyCode == LEFT){
    Yrotate = true;
  }
}

public void keyReleased(){
  Xrotate = false;
  Yrotate = false;
  warningRotate = "";
}

public void textPart(){
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
      text(times.date + " July 2019", interacts.mapRight-110,408.51063f);
      
      fill(statistics[0].lineColor[times.start_date]);
      text(times.datesWeek[times.start_date], interacts.mapRight-150,408.51063f);
      
      textFont(f1, 20);
      fill(255);
      text("Building Display", 20,(height/1.4f)-139);
      text("Light Option", 20,(height/1.4f)-91);
      text("Reset Hour", 20, (height/1.4f)-43);
      text("Reset View", 20,(height/1.4f)+5);
      
      textFont(f1, 15);
      fill(255);
      text("Pick-Up", 65,340);
      text("Drop-Off", 165,340);
      
      if((mouseX > 200-37.5f && mouseX < 200+37.5f) && (mouseY > (height/1.5f)-15 && mouseY <(height/1.5f)+15)){
        if(mousePressed && (mouseButton == LEFT)){
          textFont(f1, 25);
          fill(255);
          text("Hour Reset", interacts.mapRight-20, height/2.75f);
        }
      }
      if((mouseX > 200-37.5f && mouseX < 200+37.5f) && (mouseY > (height/1.4f)-15 && mouseY <(height/1.4f)+15)){
        if(mousePressed && (mouseButton == LEFT)){
          textFont(f1, 25);
          fill(255);
          text("View Reset", interacts.mapRight-20, height/1.4f);
        }
      }
      for(int i=0; i<selectDateButton.length; i++){
        if((mouseX > 27.5f+(i*30) && mouseX < 52.5f+(i*30)) && (mouseY > (436.363636f)-12.5f) && mouseY < (436.363636f)+12.5f){
          textFont(f1, 25);
          fill(50);
          text(15+i, mouseX+10, mouseY+10);
        }
      }
    }
    
    if(showGraphButton){
      textFont(font_date, 15);
      fill(255,300);
      text("15 July 2019: ", 20, (height/1.04f)-150);
      text("16 July 2019: ", 20, (height/1.04f)-125);
      text("17 July 2019: ", 20, (height/1.04f)-100);
      text("18 July 2019: ", 20, (height/1.04f)-75);
      text("19 July 2019: ", 20, (height/1.04f)-50);
      text("20 July 2019: ", 20, (height/1.04f)-25);
      text("21 July 2019: ", 20, height/1.04f);
      
      fill(50,200,50,300);
      text(dateCount[0], 100, (height/1.04f)-150);
      text(dateCount[1], 100, (height/1.04f)-125);
      text(dateCount[2], 100, (height/1.04f)-100);
      text(dateCount[3], 100, (height/1.04f)-75);
      text(dateCount[4], 100, (height/1.04f)-50);
      text(dateCount[5], 100, (height/1.04f)-25);
      text(dateCount[6], 100, height/1.04f);
      
      fill(255,300);
      textFont(font_date, 20); //250 318 412
      text("Trips at        o'clocks: ", 20, height/1.285f);
      
      textFont(font_date, 25);
      fill(255,75,75);
      text(times.minute, 87.5f, height/1.28f);
      text(barHeights[times.minute], 190, height/1.28f);

    }
}

public void textpartExpand(){
  if((mouseX > width-97.5f && mouseX < width-22.5f) && (mouseY > (height/1.275f)-15 && mouseY <(height/1.275f)+15)){
    if(!expandGraphButton && mapScreenWidth/mapScreenHeight <1.5f){
      textFont(f1, 20);
      fill(255);
      text("Expand Graph", mouseX-50, mouseY);
    }
  }
  if(expandGraphButton){
    
    //-------------------Second Graph Part-------------------!
    
    if((mouseX > width-97.5f && mouseX < width-22.5f) && (mouseY > (height/1.275f)-15 && mouseY <(height/1.275f)+15)){
      if(expandGraphButton && mapScreenWidth/mapScreenHeight <1.5f){
        textFont(f1, 20);
        fill(255);
        text("Minimize", mouseX-35, mouseY);
      }
    }
  }
}

public void quitMenuButton(){
  //-------------------Button Part-------------------
  pushMatrix();
      stroke(100,0,0,50);
      //quit checking
      if((mouseX > 167.5f && mouseX <242.5f) && (mouseY > (height*0.075f)-25 && mouseY <(height*0.075f)+25)){
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
      rect(205,height*0.075f,75,50,5,5,5,5); // --> Quit Button
      pushMatrix();
        fill(255);
        textFont(f1, 35);
        text(quit, mouseX-20, mouseY-20);
      popMatrix();
   popMatrix();
}

public void pick_dropButton(){
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

public void pauseButton(){
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

public void showBuildingButton(){
  if(showInteractButton){
  pushMatrix();
      stroke(200,100);
      //building checking
      if(showBuildingButton){
        showBuildingColor = color(0xff40A0D8);
      }
      if((mouseX > 200-37.5f && mouseX < 200+37.5f) && (mouseY > (height/1.4f)-159 && mouseY <(height/1.4f)-129)){
        if(!showBuildingButton){
          showBuildingColor = color(0xff0F4869);
        }else{
          showBuildingColor = color(0xff40A0D8);
        }
      }
      
      rectMode(CENTER);
      fill(showBuildingColor);
      rect(200,(height/1.4f)-144,75,30,5,5,5,5);
   popMatrix();
   }
}

public void lightingButton(){
  if(showInteractButton){
  pushMatrix();
    stroke(200,100);
    //origin point checking
    if((mouseX > 200-37.5f && mouseX < 200+37.5f) && (mouseY > (height/1.4f)-111 && mouseY <(height/1.4f)-81)){
      lightingColor = color(0xff40A0D8);
    }else{
        lightingColor = color(0xff0F4869);
    }
    if(lightingButton){
      lightingColor = color(0xff40A0D8);
    }else{
      lightingColor = color(0xff0F4869);
    }
    
    rectMode(CENTER);
    fill(lightingColor);
    rect(200,(height/1.4f)-96,75,30,5,5,5,5);
 popMatrix();
 }
}

public void resetHourButton(){
  if(showInteractButton){
  pushMatrix();
      stroke(200,100);
      //reset hour checking
      if((mouseX > 200-37.5f && mouseX < 200+37.5f) && (mouseY > (height/1.4f)-63 && mouseY <(height/1.4f)-33)){
        resetHourColor = color(0xff40A0D8);
      }else{
          resetHourColor = color(0xff0F4869);
      }
      
      rectMode(CENTER);
      fill(resetHourColor);
      rect(200,(height/1.4f)-48,75,30,5,5,5,5);
   popMatrix();
   }
}

public void resetCameraButton(){
  if(showInteractButton){
    pushMatrix();
      stroke(200,100);
      //reset camara checking
      if((mouseX > 200-37.5f && mouseX < 200+37.5f) && (mouseY > (height/1.4f)-15 && mouseY <(height/1.4f)+15)){
        resetCameraColor = color(0xff40A0D8);
      }else{
          resetCameraColor = color(0xff0F4869);
      }
      
      rectMode(CENTER);
      fill(resetCameraColor);
      rect(200,height/1.4f,75,30,5,5,5,5);
   popMatrix();
  }
}

public void showInteractButton(){
  pushMatrix();
      stroke(200,50);
      //Interact checking
      if((mouseX > 95 && mouseX <145) && (mouseY > (height*0.075f)-25 && mouseY <(height*0.075f)+25)){
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
      rect(120,height*0.075f,50,50,5,5,5,5); // --> Show/Hide Button
      pushMatrix();
        fill(255);
        text(showI, mouseX-20, mouseY-20);
      popMatrix();
   popMatrix();
}

public void showGraphButton(){
  pushMatrix();
      stroke(200,50);
      //graph checking
      if((mouseX > 25 && mouseX <75) && (mouseY > (height*0.075f)-25 && mouseY <(height*0.075f)+25)){
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
      rect(50,height*0.075f,50,50,5,5,5,5); // --> Show/Hide Button
      pushMatrix();
        fill(255);
        text(showG, mouseX-20, mouseY-20);
      popMatrix();
   popMatrix();
}

public void quitProgram(){
  if(quitButton){
    viewX = 0;
    viewY = 0;
    scale = 0.75f;
    translateX = 0.0f;
    translateY = 0.0f;
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

public void printAllFileNames() {
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

public void hightlightGraph(){
  if(showGraphButton){    //-------------------For the First Graph-------------------
    if((mouseX > 150 && mouseX < 450) && (mouseY < height/1.0422f && mouseY > height/1.25f)){
      analytics[0].graphColor1 = color(255,175);
      textFont(f1, 20);
      fill(255);
      text("AM",420,height/1.255f);
      
      textFont(f2, 15);
      fill(255);
      text("Hour",150,height/1.255f);
      text("Trips",420,height/1.025f);
      
      for(int i=0; i<analytics.length/2; i++){
        if(i <= times.minute){
          pushMatrix();
            translate(150,(height/1.270f)-i*13.5f);
            fill(50);
            textFont(f2, 15);
            if(mapScreenWidth/mapScreenHeight < 1.5f){
              text(barHeights[i], (barHeights[i]*7.5f)+10,i+160);
            }else{
              text(barHeights[i], (barHeights[i]*7.5f)+10,i+165);
            }
          popMatrix();
        }
      }
      
      textFont(f1, 12.5f);
      fill(255);
      for(int i=0; i<(analytics.length/2); i++){
        if(i != times.minute){
          pushMatrix();
          translate(155,(height/1.270f)-i*12.5f);
          if(mapScreenWidth/mapScreenHeight < 1.5f){
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
    if((mouseX > 470 && mouseX < 770) && (mouseY < height/1.0422f && mouseY > height/1.25f)){
      if(times.minute > 11){
        analytics[0].graphColor2 = color(255,175);
        textFont(f1, 20);
        fill(50);
        text("PM",740,height/1.255f);
        
        textFont(f1, 15);
        fill(255);
        text("Hour",470,height/1.255f);
        text("Trips",740,height/1.025f);
        
        for(int i=analytics.length/2; i<analytics.length; i++){
          if(i <= times.minute){
            pushMatrix();
              fill(50);
              translate(150,(height/1.270f)-(i-12)*13.5f);
              textFont(f2, 15);
              if(mapScreenWidth/mapScreenHeight < 1.5f){
                text(barHeights[i],320+(barHeights[i]*7.5f)+10,(i-12)+160);
              }else{
                text(barHeights[i],320+(barHeights[i]*7.5f)+10,(i-12)+165);
              }
            popMatrix();
            
          }
        }
        
        textFont(f2, 12.5f);
        fill(255);
        for(int i=(analytics.length/2); i<analytics.length; i++){
          if(i != times.minute){
            pushMatrix();
            translate(475,(height/1.270f)-i*12.5f);
            if(mapScreenWidth/mapScreenHeight < 1.5f){
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

public void hightlightSecondGraph(){
  if(expandGraphButton){   //-------------------For the Second Graph-------------------
    if(mapScreenWidth/mapScreenHeight < 1.5f){
      if((mouseX > 350 && mouseX < 850) && (mouseY < height/1.0422f && mouseY > height/1.275f)){
        statistics[0].graphColor = color(175,175);
        
        textFont(f2,12.5f);
        fill(255);
        text("Trips",340,height/1.285f);
        text("Hour",815,height/1.05f);
        for(int i=0; i<24; i++){
          pushMatrix();
          translate(365+i*4,(height/1.023f));
          text(i,i*15,0);
          popMatrix();
        }
        for(int i=0; i<7; i++){
          pushMatrix();
          textFont(f2,12.5f);
          translate(330,(height/1.045f)-i*4.5f);
          text(i*5,0,-i*20);
          popMatrix();
        }
        textFont(f2,15);
        fill(50);
        noStroke();
        rect(820, (height/1.275f), 850, (height/1.275f)+125);
        for(int i=0; i<statistics.length; i++){
          if(lineGraphButton[i]){
            fill(statistics[i].lineColor[i]);
            text(statistics[i].sumDate, 825,(height/1.25f)+(i*17.5f));
          }
        }
      }else{
        statistics[0].graphColor = color(150,0);
      }
    }
  }if(mapScreenWidth/mapScreenHeight > 1.5f){
      if((mouseX > width/2+300 && mouseX < width/2+800) && (mouseY < height/1.0422f && mouseY > height/1.275f)){
        statistics[0].graphColor = color(255,175);
        
        textFont(f2,12.5f);
        fill(255);
        text("Trips",width/2+290,height/1.285f);
        text("Hour",width/2+765,height/1.05f);
        for(int i=0; i<24; i++){
          pushMatrix();
          translate(width/2+305+i*4,(height/1.023f));
          text(i,i*15,0);
          popMatrix();
        }
        for(int i=0; i<7; i++){
          pushMatrix();
          textFont(f2,12.5f);
          translate(width/2+280,(height/1.045f)-i*4.5f);
          text(i*5,0,-i*20);
          popMatrix();
        }
        textFont(f2,15);
        fill(50);
        noStroke();
        rect(width/2+770, (height/1.275f), width/2+800, (height/1.275f)+125);
        for(int i=0; i<statistics.length; i++){
          if(lineGraphButton[i]){
            fill(statistics[i].lineColor[i]);
            text(statistics[i].sumDate, width/2+775,(height/1.25f)+(i*17.5f));
          }
        }
      }else{
        statistics[0].graphColor = color(150,0);
      }
  }
}

public void selectDateButton(){
  if(showInteractButton){
    pushMatrix();
    
    int[] dateButton = new int[7];
    noStroke();
    for(int i=0; i<dateButton.length; i++){
      rectMode(CENTER);
      fill(selectDateColor[i]);
      rect(40+(i*30), 436.363636f, 25,25);
    }
    interacts.presentDate();
    fill(selectDateColor[times.start_date],25);
    rect(40+(times.start_date*30),436.363636f, 25,25);
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

public void increaseDecreaseDateButton(){
  int low;
  int high;
  if(mapScreenWidth/mapScreenHeight > 1.5f){
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

public void ampmButton(){
  int low;
  int high;
  if(mapScreenWidth/mapScreenHeight > 1.5f){
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

public void expandGraph(){
  if(showGraphButton && mapScreenWidth/mapScreenHeight < 1.5f){
    pushMatrix();
      noStroke();
      //expand graph checking
      if((mouseX > width-97.5f && mouseX < width-22.5f) && (mouseY > (height/1.275f)-15 && mouseY <(height/1.275f)+15)){
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
      rect(width-60,height/1.275f,75,30);
   popMatrix();
  }
  if(mapScreenWidth/mapScreenHeight > 1.5f){
    expandGraphButton = false;
  }
}

public void statisticsData(){
  if(expandGraphButton || mapScreenWidth/mapScreenHeight > 1.5f){
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

public void textStatisticsData(){
  if(showGraphButton){
    if(expandGraphButton || mapScreenWidth/mapScreenHeight > 1.5f){
      if(mapScreenWidth/mapScreenHeight < 1.5f){
          textFont(f2,17.5f);
          fill(255,300);
          text("The Most Trips per Hour: ", 20, height/1.275f);
          text("The Least Trips per Hour: ", 20, height/1.275f+25);
          text("The Most Trips per Day: ", 20, height/1.275f+65);
          text("The Least Trips per Day: ", 20, height/1.275f+90);
          text("Average Trips per Hour: ", 20, height/1.275f+130);
          text("Average Trips per Day: ", 20, height/1.275f+155);
          
          text(stat_max, 200, height/1.275f);
          text(stat_dateMax, 200, height/1.275f+65);
          
          if(filterData.length > 0){
            text(stat_min, 200, height/1.275f+25);
            text(stat_dateMin, 200, height/1.275f+90);
          }else{
            text(0, 200, height/1.275f+25);
            text(0, 200, height/1.275f+90);
          }
          
          text(avgDate, 200, height/1.275f+130);
          text(avgWeek, 200, height/1.275f+155);
          
        }else{
          textFont(f2,17.5f);
          fill(255,300);
          text("The Most Trips per Hour: ", width/2-100, height/1.275f);
          text("The Least Trips per Hour: ", width/2-100, height/1.275f+25);
          text("The Most Trips per Day: ", width/2-100, height/1.275f+65);
          text("The Least Trips per Day: ", width/2-100, height/1.275f+90);
          text("Average Trips per Hour: ", width/2-100, height/1.275f+130);
          text("Average Trips per Day: ", width/2-100, height/1.275f+155);
          
          text(stat_max, width/2+80, height/1.275f);
          text(stat_dateMax, width/2+80, height/1.275f+65);
          
          if(filterData.length > 0){
            text(stat_min, width/2+80, height/1.275f+25);
            text(stat_dateMin, width/2+80, height/1.275f+90);
          }else{
            text(0, width/2+80, height/1.275f+25);
            text(0, width/2+80, height/1.275f+90);
          }
          
          text(avgDate, width/2+80, height/1.275f+130);
          text(avgWeek, width/2+80, height/1.275f+155);
        }
    }
  }
}

public void resetZero(){
  stat_max =0;
  stat_dateMax =0;
  sumDate=0;
  sumWeek=0;
  avgDate =0;
  avgWeek =0;
  filterData = new int[0];
}

public void daylight(){
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

public void helpingMenuPanel(){
  noStroke();
  if(mapScreenWidth/mapScreenHeight < 1.5f){
    if((mouseX>100 && mouseX<150) && (mouseY>(height*0.075f)-25) && mouseY<(height*0.075f)+25){
      helpingMenuColor = color(175);
      help = "Swith Page";
    }else{
      helpingMenuColor = color(250);
      help = "";
    }
    fill(helpingMenuColor);
    rect(125,height*0.075f,50,50,5,5,5,5); // ---HelpingMenu1
    
    fill(255);
    textFont(f1, 20);
    text(help, mouseX, mouseY-20);
    
  }
  fill(255,150);
  textFont(f2, 17.5f);
  text("Powered by Processing 3.0", width-200, 40);
  text("Featuring with Google Maps API", width-232.5f, 60);
  text("Map Theme by Mapbox", width-170, 80);
  text("Project Version: 0.28.0", width-170, 100);
}

public void helpingMenu1(){
  if(mapScreenWidth/mapScreenHeight < 1.5f){
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
    vertex((width*0.5102040f)-100,150,1000,0);
    vertex((width*0.5102040f)-100,height-50,1000,672);
    vertex(100,height-50,0,672);
    endShape();
  }
}

public void helpingMenu2(){
  if(mapScreenWidth/mapScreenHeight < 1.5f){
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

public void rotateLeftRight(){
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
  if(mapScreenWidth/mapScreenHeight < 1.5f){
    if(viewY < -1000){ viewY = 0;}
    if(viewY >  1000){ viewY = 0;}
  }else{
    if(viewY < -1920){ viewY = 0;}
    if(viewY >  1920){ viewY = 0;}
  }
}

public void rotateTopBot(){
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

public void drawPattern() {
  for (int iter = 0; iter < width; iter+=5) {
    float h = random(height-150,height);
    stroke(255,0, h/(500+(height-100)) * 255, 100);
    line(iter,height,iter,h);
  }
}
class Building{
  float h;
  float posX;
  float posY;
  
  Building(float posX_, float posY_ , float h_){
    h = h_;
    if(width/height >1.5f){
      posX = posX_*1.05f;
    }else{
      posX = posX_;
    }
    
    if(height ==1001){
      posY = posY_*1.05f;
    }else{
      posY = posY_;
    }
  }
  
  Building(){
  
  }
  
  public void drawBuildingTall(){
    pushMatrix();
      translate(posX,-h/2,posY);
      fill(255,0,255,h*4);
      box(5, h, 5);
    popMatrix();
  }
  
  public void drawBuildingUniversity(){
    pushMatrix();
      translate(posX,-h/2,posY);
      fill(255,0,255,200);
      box(h*5, h, h*5);
    popMatrix();
    
    pushMatrix();
      translate(posX/0.95f,-h,posY/0.875f);
      fill(255,0,255,200);
      box(h*8, h*3, h*2.5f);
    popMatrix();
  }
  
  public void drawBuildingHospital(){
    pushMatrix();
      translate(posX,-h,posY);
      fill(255,0,255,100);
      box(h, h*2, h*2);
    popMatrix();
  }
  
  public void drawBuildingMall(){
    pushMatrix();
      translate(posX,-h/2,posY);
      fill(255,0,255,100);
      box(h*2, h/2, h*1.5f);
    popMatrix();
  }
  
  public void initDowntown(Building[] buildingsDowntown){
    buildingsDowntown[0] = new Building(-40,-50,20);
    buildingsDowntown[1] = new Building(-32.5f,-50,15);
    buildingsDowntown[2] = new Building(-25,-50,25);
    buildingsDowntown[3] = new Building(-17.5f,-50,30);
    buildingsDowntown[4] = new Building(-30,-60,25);
    buildingsDowntown[5] = new Building(-20,-60,40);
    buildingsDowntown[6] = new Building(-47.5f,-50,22);
    buildingsDowntown[7] = new Building(-10,-50,25);
    buildingsDowntown[8] = new Building(2.5f,-50,30);
    buildingsDowntown[9] = new Building(10,-50,23);
    buildingsDowntown[10] = new Building(-30,-40,23);
    buildingsDowntown[11] = new Building(-20,-40,25);
    buildingsDowntown[12] = new Building(-47.5f,-40,20);
    buildingsDowntown[13] = new Building(-10,-40,30);
    buildingsDowntown[14] = new Building(2.5f,-40,27);
    buildingsDowntown[15] = new Building(10,-40,20);
    buildingsDowntown[16] = new Building(2.5f,-60,20);
    buildingsDowntown[17] = new Building(-42,-60,20);
    buildingsDowntown[18] = new Building(-55,-60,20);
    buildingsDowntown[19] = new Building(-37.5f,-32.5f,15);
    buildingsDowntown[20] = new Building(-47.5f,-32.5f,15);
    buildingsDowntown[21] = new Building(-71.5f,-28,15);
    buildingsDowntown[22] = new Building(-71.5f,-35,18);
    buildingsDowntown[23] = new Building(-79,-28,10);
    buildingsDowntown[24] = new Building(-80,-35,20);
    buildingsDowntown[25] = new Building(-22.5f,-32.5f,22);
    buildingsDowntown[26] = new Building(-7,-29.5f,22);
    buildingsDowntown[27] = new Building(-74,-51.5f,25);
    buildingsDowntown[28] = new Building(-90,-51,15);
    buildingsDowntown[29] = new Building(-97.5f,-51,15);
    buildingsDowntown[30] = new Building(-37.5f,-20,17);
    buildingsDowntown[31] = new Building(-15,-20,15);
    buildingsDowntown[32] = new Building(-23,-20,15);
    buildingsDowntown[33] = new Building(-48.5f,5,12);
    buildingsDowntown[34] = new Building(-35,-2,17.5f);
    buildingsDowntown[35] = new Building(-82,0,12);
    buildingsDowntown[36] = new Building(-75,0,15);
    buildingsDowntown[37] = new Building(-15,-5,12);
    buildingsDowntown[38] = new Building(-5,-15,15);
  }
  
  public void initHospital(Building[] hospital){
    hospital[0] = new Building(-223,-140.5f,7);
    hospital[1] = new Building(-92.5f,120,5);
    hospital[2] = new Building(17.5f,-50,3);
    hospital[3] = new Building(3,-30,3);
    hospital[4] = new Building(-16,-30,3);
    hospital[5] = new Building(-180,60,6);
    hospital[6] = new Building(-50,222.5f,10);
    hospital[7] = new Building(-32.5f,100,5);
    hospital[8] = new Building(46,-237.5f,7);
    hospital[9] = new Building(70,-237.5f,7);
    hospital[10] = new Building(45,-170,5);
    hospital[11] = new Building(-16,-97.5f,5);
    hospital[12] = new Building(-22.5f,-97.5f,5);
    hospital[13] = new Building(213,-232.5f,4);
    hospital[14] = new Building(-109,-92.5f,4);
    hospital[15] = new Building(-80,-95,5);
  }
  
  public void initMall(Building[] mall){
    mall[0] = new Building(-277.5f,33.5f,6);
    mall[1] = new Building(-277.5f,48,7);
    mall[2] = new Building(-62.5f,-51,5);
    mall[3] = new Building(-60,-30,7);
    mall[4] = new Building(17.5f,-27.5f,8);
    mall[5] = new Building(17.5f,-15.5f,7);
    mall[6] = new Building(-190,60,5);
    mall[7] = new Building(25,-39,4);
    mall[8] = new Building(34,-39,4);
    mall[9] = new Building(26,-49,4);
    mall[10] = new Building(37,-47,4);
    mall[11] = new Building(-94,-31,7);
    mall[12] = new Building(-110,-31,7);
    mall[13] = new Building(-122.5f,-30,4);
    mall[14] = new Building(-122.5f,-37,4);
    mall[15] = new Building(-46,-20,4);
    mall[16] = new Building(-60,-20,4);
    mall[17] = new Building(-70,-20,4);
    mall[18] = new Building(-80,-20,4);
    mall[19] = new Building(-92.5f,-20,4);
    mall[20] = new Building(-102.5f,-20,4);
    mall[21] = new Building(-112.5f,-20,4);
    mall[22] = new Building(-122.5f,-20,4);
    mall[23] = new Building(-92.5f,-13,4);
    mall[24] = new Building(-102.5f,-13,4);
    mall[25] = new Building(-92.5f,-6,4);
    mall[26] = new Building(-102.5f,-6,4);
    mall[27] = new Building(-117.5f,-10,7);
    mall[28] = new Building(-65,-10,5.5f);
    mall[29] = new Building(-65,2,5.5f);
    mall[30] = new Building(-78.5f,-10,5.5f);
    mall[31] = new Building(-78.5f,2,5.5f);
    mall[31] = new Building(-48.5f,-12,4);
    mall[32] = new Building(-50.5f,-5,4);
    mall[33] = new Building(-3,-8,3.5f);
    mall[34] = new Building(25,38.5f,5);
    mall[35] = new Building(14,38.5f,5);
    mall[36] = new Building(27,30,3.5f);
    mall[37] = new Building(19,30,3.5f);
    mall[38] = new Building(11,30,3.5f);
    mall[39] = new Building(27,23,3.5f);
    mall[40] = new Building(19,23,3.5f);
    mall[41] = new Building(11,23,3.5f);
    mall[42] = new Building(27,16,3.5f);
    mall[43] = new Building(19,16,3.5f);
    mall[44] = new Building(11,16,3.5f);
    mall[45] = new Building(13,6,6);
    mall[46] = new Building(25,7,5);
    mall[47] = new Building(35,8,4);
    mall[48] = new Building(61,41,3.5f);
    mall[49] = new Building(53,41,3.5f);
    mall[50] = new Building(45,41,3.5f);
    mall[51] = new Building(37,41,3.5f);
    mall[52] = new Building(61,34,3.5f);
    mall[53] = new Building(53,34,3.5f);
    mall[54] = new Building(45,34,3.5f);
    mall[55] = new Building(37,34,3.5f);
    mall[56] = new Building(69,27,3.5f);
    mall[57] = new Building(61,27,3.5f);
    mall[58] = new Building(53,27,3.5f);
    mall[59] = new Building(45,27,3.5f);
    mall[60] = new Building(52,18,6);
    mall[61] = new Building(67,18,6);
    mall[62] = new Building(69,9,3.5f);
    mall[63] = new Building(56,-33,3.5f);
    mall[64] = new Building(56,-26,3.5f);
    mall[65] = new Building(56,-19,3.5f);
    mall[66] = new Building(56,-12,3.5f);
    mall[67] = new Building(56,-5,3.5f);
    mall[68] = new Building(48,-8.5f,3.5f);
    mall[69] = new Building(41,-1.5f,3.5f);
    mall[70] = new Building(35,-12,3.5f);
  }
  public void initNorthMall(Building[] northMall){
    northMall[0] = new Building(-78.5f,-121,5);
    northMall[1] = new Building(-67.5f,-121,5);
    northMall[2] = new Building(-52.5f,-121,5);
    northMall[3] = new Building(-42,-122,4);
    northMall[4] = new Building(-32,-122,4);
    northMall[5] = new Building(-20,-121,4);
    northMall[6] = new Building(-11,-121,4);
    northMall[7] = new Building(4,-121,5);
    northMall[8] = new Building(-20,-110,4);
    northMall[9] = new Building(-11,-110,4);
    northMall[10] = new Building(4,-109,5);
    northMall[11] = new Building(-9,-100,3);
    northMall[12] = new Building(-9,-94,3);
    northMall[13] = new Building(4,-100,4);
    northMall[14] = new Building(23.5f,-98.5f,5);
    northMall[15] = new Building(17.5f,-109,3.5f);
    northMall[16] = new Building(26,-109,3.5f);
    northMall[17] = new Building(18,-72.5f,4);
    northMall[18] = new Building(51.5f,-109,5);
    northMall[19] = new Building(90,-77.5f,4);
    northMall[20] = new Building(30,-80,4);
  }
  
  public void initSouthMall(Building[] southMall){
    southMall[0] = new Building(-35,242.5f,5);
    southMall[1] = new Building(-35,232.5f,5);
    southMall[2] = new Building(-50,240,8);
    southMall[3] = new Building(-35,222.5f,5);
    southMall[4] = new Building(-35,212.5f,5);
    southMall[5] = new Building(-15,242.5f,4);
    southMall[6] = new Building(-5,242.5f,4);
    southMall[7] = new Building(47.5f,155,4);
    southMall[8] = new Building(37.5f,145,4);
    southMall[9] = new Building(-21,95.5f,3.5f);
    southMall[10] = new Building(-12.5f,95.5f,3.5f);
  }
}
class Graph{
  int time;
  int position;
  int timeCounter;
  int barHeight;
  int[] data;
  int graphColor1, graphColor2;
  
  Graph(int time_, String[] data_, int position_){
    time = time_;
    position = position_;
    timeCounter = 0;
    barHeight =0;
    data = PApplet.parseInt(data_);
    graphColor1 = color(150,0);
    graphColor2 = color(150,0);
  }
  
  public void graphSetup(){
    ////background
    fill(255,80);
    noStroke();
    rectMode(LEFT);
    rect(10,height/1.02f,width-10,height/1.322f);
    
    fill(graphColor1);
    rectMode(CORNERS);
    rect(150,height/1.0422f,450,height/1.25f);
    
    if(!expandGraphButton){
      stroke(0);
      strokeWeight(3);
      line(150,height/1.0422f,450,height/1.0422f);
      line(150,height/1.0422f,150,height/1.25f);
    }
    
    
    if(times.minute >= 12){
      noStroke();
      fill(graphColor2);
      rectMode(CORNERS);
      rect(470,height/1.0422f,770,height/1.25f);
      
      if(!expandGraphButton){
        stroke(0);
        strokeWeight(3);
        line(470,height/1.0422f,770,height/1.0422f);
        line(470,height/1.0422f,470,height/1.25f);
      }
      
    }
    
  }
  public void graphCounter(){
      timeCounter=0;
      if(times.minute == position){
        for(int j = 0; j < data.length; j++){
          if(PApplet.parseInt(data[j]) ==position){
            timeCounter++;
          }
        }
      }   
      barHeight = timeCounter;
  }

}
class GraphV2{
  String[] dataTime;
  String[] dataLine;
  String[] dateName;
  int[] lineData;
  int[] lineColor;
  int graphColor;
  int filterMin, filterMax, filterSwap;
  int min, max;
  int dateMin, dateMax;
  int sumDate, avgDate;
  
  GraphV2(int index){
    dataTime = loadStrings(url_timeOutput[index]);
    dataLine = splitTokens(dataTime[0],",\"");
    lineData = new int[24];
    lineColor = new int[7];
    dateName = new String[7];
    graphColor = color(150,0);
    dateMin = dataLine.length;
    min = 0;
  }
  public void secondGraphSetup(){
    if(showGraphButton){
      noStroke();
      pushMatrix();
      if(expandGraphButton){
        if(mapScreenWidth/mapScreenHeight < 1.5f){
          fill(graphColor);
          rectMode(CORNERS);
          rect(350,height/1.0422f,850,height/1.275f);
          
          stroke(0);
          strokeWeight(3);
          line(350,height/1.0422f,850,height/1.0422f);
          line(350,height/1.0422f,350,height/1.275f);
          
          textFont(f2, 15);
          fill(255);
          text("Total of trips in each day (7 days)", 500, height/1.280f);
        }
      }
      if(mapScreenWidth/mapScreenHeight > 1.5f){
          fill(graphColor);
          rectMode(CORNERS);
          rect(width/2+300,height/1.0422f,width/2+800,height/1.275f);
          
          stroke(0);
          strokeWeight(3);
          line(width/2+300,height/1.0422f,width/2+800,height/1.0422f);
          line(width/2+300,height/1.0422f,width/2+300,height/1.275f);
          
          textFont(f2, 15);
          fill(255);
          text("Total of trips in each day (7 days)", width/2+450, height/1.280f);
      }
        popMatrix();
    }
  }
  
  public void initLineGraph(){
    for(int i=0; i<dataLine.length; i++){
      for(int j=0; j<24; j++){
        if(PApplet.parseInt(dataLine[i]) == j){
          lineData[j] += 1;
        }
      }
    }
    lineColor[0] = color(0xffF7F65E); //Monday
    lineColor[1] = color(0xffEC4EAE); //Tuesday
    lineColor[2] = color(0xff35B41E); // Wednesday
    lineColor[3] = color(0xffE58B26); // Thursday
    lineColor[4] = color(0xff20C9D5); // Friday
    lineColor[5] = color(0xffA92DDA); // Saturday
    lineColor[6] = color(0xffF43A3A); // Sunday
    
    dateName[0] = "Monday";
    dateName[1] = "Tuesday";
    dateName[2] = "Wednesday";
    dateName[3] = "Thursday";
    dateName[4] = "Friday";
    dateName[5] = "Saturday";
    dateName[6] = "Sunday";
  }
  
  public void secondGraphBG(){
    if(showGraphButton){
      if(expandGraphButton){
        //background
        fill(100);
        noStroke();
        rectMode(LEFT);
        rect(10,height/1.02f,width-10,height/1.322f);
      }
    }
  }
  
  public void secGrph_line(int date,int inHour){
    if(showGraphButton){
      if(expandGraphButton || mapScreenWidth/mapScreenHeight > 1.5f){
        float xAxis = 0;
        float yAxis = height/1.05f;
        if(mapScreenWidth/mapScreenHeight > 1.5f){
          xAxis = width/2+310;
        }else{
          xAxis = 370;
        }
        if(lineGraphButton[date]){
          pushMatrix();
            translate(inHour*15,yAxis);
            noStroke();
            fill(lineColor[date], 150);
            ellipse(xAxis+(inHour*4),-(lineData[inHour]*4), 4,4);
          
            if(inHour>0){
              translate(-20,0);
              stroke(lineColor[date], 100);
              strokeWeight(3);
              line(xAxis+(inHour*4),-(lineData[inHour-1]*4),xAxis+(inHour*4)+20,-(lineData[inHour]*4));
            }
          popMatrix();
        }
      }
    }
  }
  
  public void graphStatic(){
      if(showGraphButton){
        sumDate = 0;
        int[] grphStat_lineData = new int[24];
        for(int i=0; i<dataLine.length; i++){
          for(int j=0; j<24; j++){
            if(PApplet.parseInt(dataLine[i]) == j){
                grphStat_lineData[j] += 1;
              }
            }
          }
          for(int i=0; i<grphStat_lineData.length; i++){
            sumDate += grphStat_lineData[i];
          }
          avgDate = sumDate/grphStat_lineData.length;
          
          //Check Trips per Day
          if(dataLine.length >= dateMin){
            if(dataLine.length >= dateMax){
              dateMax = dataLine.length;
            }
          }else{
            dateMin = dataLine.length;
          }
          
          //Check Trips per Hour
          min = grphStat_lineData[0];
          for(int i=1; i<grphStat_lineData.length; i++){
            if(grphStat_lineData[i] >= min){
              if(grphStat_lineData[i] >= max){
                max = grphStat_lineData[i];
              }
            }else{
              min = grphStat_lineData[i];
            }
          }
        }
  }
  
  public void filterLineGraph(){
    if(showGraphButton){
      textFont(f2,15);
      if(expandGraphButton){
        fill(255,25);
        rectMode(LEFT);
        rect(855,(height/1.2f)-15,985,(height/1.2f)+45);
        if((mouseX > 875-10 && mouseX < 875+10) && (mouseY > (height/1.2f)-10 && mouseY < (height/1.2f)+10)){
          fill(255);
          text("Default", mouseX-20, mouseY-20);
        }else{
          fill(150);
        }
        rectMode(CENTER);
        pushMatrix();
          translate(875,height/1.2f);
          rect(0,0,20,20);
        popMatrix();
        for(int i=1; i<statistics.length+1; i++){
          if(i-1<(statistics.length-1)/2){
            if((mouseX > 875+(i*30)-10 && mouseX < 875+(i*30)+10) && (mouseY > (height/1.2f)-10 && mouseY < (height/1.2f)+10)){
              fill(statistics[i-1].lineColor[i-1],250);
              text(statistics[i-1].dateName[i-1],mouseX-((i*i)*5), mouseY-20);
            }else{
              fill(statistics[i-1].lineColor[i-1],100);
            }
            pushMatrix();
              translate(875+(i*30),height/1.2f);
              rect(0,0,20,20);
            popMatrix();
          }else{
            if((mouseX > 875+((i-4)*30)-10 && mouseX < 875+((i-4)*30)+10) && (mouseY > (height/1.2f)+20 && mouseY < (height/1.2f)+40)){
              fill(statistics[i-1].lineColor[i-1],250);
              text(statistics[i-1].dateName[i-1],mouseX-(i*5), mouseY+40);
            }else{
              fill(statistics[i-1].lineColor[i-1],100);
            }
            pushMatrix();
              translate(875+((i-4)*30),(height/1.2f)+30);
              rect(0,0,20,20);
            popMatrix();
          }
        }
      }
      if(mapScreenWidth/mapScreenHeight > 1.5f){
        fill(255,25);
        rectMode(LEFT);
        rect(width/2+805,(height/1.2f)-15,width/2+935,(height/1.2f)+45);
        if((mouseX > width/2+825-10 && mouseX < width/2+825+10) && (mouseY > (height/1.2f)-10 && mouseY < (height/1.2f)+10)){
          fill(255);
          text("Default", mouseX-20, mouseY-20);
        }else{
          fill(150);
        }
        rectMode(CENTER);
        pushMatrix();
          translate((width/2)+825,height/1.2f);
          rect(0,0,20,20);
        popMatrix();
        for(int i=1; i<statistics.length+1; i++){
          if((mouseX > width/2+825+(i*30)-10 && mouseX < width/2+825+(i*30)+10) && (mouseY > (height/1.2f)-10 && mouseY < (height/1.2f)+10)){
            fill(statistics[i-1].lineColor[i-1],250);
            text(statistics[i-1].dateName[i-1],mouseX-((i*i)*5), mouseY-20);
          }else{
            fill(statistics[i-1].lineColor[i-1],150);
          }
          if(i-1<(statistics.length-1)/2){
            pushMatrix();
            translate(width/2+825+(i*30),height/1.2f);
            rect(0,0,20,20);
            popMatrix();
          }else{
            if((mouseX > width/2+825+((i-4)*30)-10 && mouseX < width/2+825+((i-4)*30)+10) && (mouseY > (height/1.2f)+20 && mouseY < (height/1.2f)+40)){
              fill(statistics[i-1].lineColor[i-1],250);
              text(statistics[i-1].dateName[i-1],mouseX-(i*5), mouseY+40);
            }else{
              fill(statistics[i-1].lineColor[i-1],150);
            }
            pushMatrix();
            translate(width/2+825+((i-4)*30),(height/1.2f)+30);
            rect(0,0,20,20);
            popMatrix();
          }
        }
      }
    }
  }
  
  public void filterNumberGraph(){
    if(showGraphButton){
      if(expandGraphButton){
        if((mouseX > 870-10 && mouseX < 870+10) && (mouseY > (height/1.105f)-12.5f) && mouseY < (height/1.105f)+12.5f){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(870,(height/1.105f),20,25);
        
        if((mouseX > 930-10 && mouseX < 930+10) && (mouseY > (height/1.105f)-12.5f) && mouseY < (height/1.105f)+12.5f){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(930,(height/1.105f),20,25);
        
        fill(50);
        rect(900,(height/1.105f),30,30);
        fill(255);
        textFont(f2,20);
        text(filterMin,892.5f,(height/1.105f)+7.5f);
        
        if((mouseX > 870-10 && mouseX < 870+10) && (mouseY > (height/1.055f)-12.5f) && mouseY < (height/1.055f)+12.5f){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(870,(height/1.055f),20,25);
        
        if((mouseX > 930-10 && mouseX < 930+10) && (mouseY > (height/1.055f)-12.5f) && mouseY < (height/1.055f)+12.5f){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(930,(height/1.055f),20,25);
  
        if((mouseX > 965-15 && mouseX < 965+15) && (mouseY > (height/1.075f)-25) && mouseY < (height/1.075f)+25){
          fill(0,255,0,250);
        }else{
          fill(0,255,0,50);
        }
        rect(965,(height/1.075f),30,50);
        
        fill(50);
        rect(900,(height/1.055f),30,30);
        fill(255);
        textFont(f2,20);
        text(filterMax,892.5f,(height/1.055f)+7.5f);
        
      }
      if(mapScreenWidth/mapScreenHeight > 1.5f){
        if((mouseX > width/2+820-10 && mouseX < width/2+820+10) && (mouseY > (height/1.105f)-12.5f) && mouseY < (height/1.105f)+12.5f){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(width/2+820,(height/1.105f),20,25);
        
        if((mouseX > width/2+880-10 && mouseX < width/2+880+10) && (mouseY > (height/1.105f)-12.5f) && mouseY < (height/1.105f)+12.5f){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(width/2+880,(height/1.105f),20,25);
        
        fill(50);
        rect(width/2+850,(height/1.105f),30,30);
        fill(255);
        textFont(f2,20);
        text(filterMin,width/2+842.5f,(height/1.105f)+7.5f);
        
        if((mouseX > width/2+820-10 && mouseX < width/2+820+10) && (mouseY > (height/1.055f)-12.5f) && mouseY < (height/1.055f)+12.5f){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(width/2+820,(height/1.055f),20,25);
        
        if((mouseX > width/2+880-10 && mouseX < width/2+880+10) && (mouseY > (height/1.055f)-12.5f) && mouseY < (height/1.055f)+12.5f){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(width/2+880,(height/1.055f),20,25);
        
        if((mouseX > width/2+915-10 && mouseX < width/2+915+10) && (mouseY > (height/1.075f)-25) && mouseY < (height/1.075f)+25){
          fill(0,255,0,250);
        }else{
          fill(0,255,0,50);
        }
        rect(width/2+915,(height/1.075f),30,50);
        
        fill(50);
        rect(width/2+850,(height/1.055f),30,30);
        fill(255);
        textFont(f2,20);
        text(filterMax,width/2+842.5f,(height/1.055f)+7.5f);
      }
    }
  }
}
class Interact{
  float mapLeft = width/50; //200
  float mapRight = 250;
  float mapTop = height/7.65f; //125.4901
  float mapBottom = height/3; //320
  float oPointX, oPointY;
  float dPointX, dPointY;
  float minimapWidth, minimapHeight;
  
  Interact(){
    if(mapScreenWidth/mapScreenHeight > 1.5f){
      mapRight = 250;
    }
    minimapWidth = (mapRight*0.95f)+20;
    minimapHeight = mapTop*1.5f;
  }
  public void interactionInit(){
    fill(255,80);
    noStroke();
    rectMode(LEFT);
    rect(10,height/8.35f,mapRight,height/1.35f);
  }
  
  public void miniMap(){
    
    pushMatrix();
      if(mapScreenWidth/mapScreenHeight < 1.5f){
        beginShape();
        texture(backgroundMap);
        vertex(mapLeft,mapTop,0,0);
        vertex(mapRight*0.95f,mapTop,851,0);
        vertex(mapRight*0.95f,mapBottom,851,1058);
        vertex(mapLeft,mapBottom,0,1058);
        endShape();
      }else{
        mapRight = 250;
        mapLeft = 20;
        mapTop = 125.4901f;
        mapBottom = 320;
        beginShape();
        texture(backgroundMap);
        vertex(mapLeft,mapTop,0,0);
        vertex(mapRight*0.95f,mapTop,851,0);
        vertex(mapRight*0.95f,mapBottom,851,1058);
        vertex(mapLeft,mapBottom,0,1058);
        endShape();
      }
      
      strokeWeight(3);
      stroke(255);
      noFill();
      rectMode(CORNER);
      rect(mapLeft, mapTop, mapRight*0.875f, mapBottom-mapTop);
    popMatrix();
  }
  
  public void originDisplay(String[] s){
    oPointX = minimapWidth*(PApplet.parseFloat(s[1])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
    oPointY = mapBottom-minimapHeight*(PApplet.parseFloat(s[0])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
    
    if(pickupButton){
      pushMatrix();
        noStroke();
        fill(255,0,0,150);
        ellipseMode(CENTER);
        ellipse(oPointX,oPointY,5,5);
      popMatrix();
    }
  }
  
  public void destinationDisplay(String[] s){
    dPointX = minimapWidth*(PApplet.parseFloat(s[s.length-1])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
    dPointY = mapBottom-minimapHeight*(PApplet.parseFloat(s[s.length-2])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
    
    if(dropoffButton){
      pushMatrix();
        noStroke();
        fill(255,150);
        ellipseMode(CENTER);
        ellipse(dPointX,dPointY,5,5);
      popMatrix();
    }
  }
  
  public void presentDate(){
    if(showInteractButton){
      for(int i=0; i<selectDateColor.length; i++){
        if(mapScreenWidth/mapScreenHeight < 1.5f){
          if((mouseX > 27.5f+(i*30) && mouseX < 52.5f+(i*30)) && (mouseY > (height/2.2f)-12.5f) && mouseY < (height/2.2f)+12.5f){
            selectDateColor[i] = color(255,0,0,150);
            if(mousePressed && mouseButton == LEFT){
              selectDateButton[i] = true;
              selectDateColor[i] = color(255);
              times.start_date = i;
              times.date =15+i;
              times.continueDate();
            }
          }else{
            if(times.start_date == i){
              selectDateColor[i] = color(255);
            }else{
              selectDateColor[i] = color(175,150);
            }
          }
        }else{
          if((mouseX > 27.5f+(i*30) && mouseX < 52.5f+(i*30)) && (mouseY > (436.363636f)-12.5f) && mouseY < (436.363636f)+12.5f){
            selectDateColor[i] = color(255,0,0,150);
            if(mousePressed && mouseButton == LEFT){
              selectDateButton[i] = true;
              selectDateColor[i] = color(255);
              times.start_date = i;
              times.date =15+i;
              times.continueDate();
            }
          }else{
            if(times.start_date == i){
              selectDateColor[i] = color(255);
            }else{
              selectDateColor[i] = color(175,150);
            }
          }
        }
      }
    }
  }
  
}
class Scrollbar {
  int swidth, sheight;    // width and height of bar
  float xpos, ypos;       // x and y position of bar
  float spos, newspos;    // x position of slider
  float sposMin, sposMax; // max and min values of slider
  int loose;              // how loose/heavy
  boolean over;           // is the mouse over the slider?
  boolean locked;
  float ratio;

  Scrollbar (float xp, float yp, int sw, int sh) {
    swidth = sw;
    sheight = sh;
    int widthtoheight = sw - sh;
    ratio = (float)sw / (float)widthtoheight;
    xpos = xp;
    ypos = yp-sheight/2;
    spos = xpos;
    newspos = spos;
    sposMin = xpos;
    sposMax = xpos + swidth - sheight;
  }

  public void update() {
    if(showInteractButton){
      if (overEvent()) {
        over = true;
      } else {
        over = false;
      }
      if (mousePressed && over) {
        locked = true;
      }
      if (!mousePressed) {
        locked = false;
      }
      if (locked) {
        newspos = constrain(mouseX-sheight/2, sposMin, sposMax);
      }
      if (abs(newspos - spos) > 1) {
        spos = spos + (newspos-spos);
      }
    }
  }

  public float constrain(float val, float minv, float maxv) {
    return min(max(val, minv), maxv);
  }

  public boolean overEvent() {
    if (mouseX > xpos && mouseX < xpos+swidth &&
       mouseY > ypos && mouseY < ypos+sheight) {
      return true;
    } else {
      return false;
    }
  }

  public void display() {
    if(times.minute > 11){
      spos = xpos + (times.minute-12)*17.5f;
    }else{
      spos = xpos + times.minute*17.5f;
    }
    if(showInteractButton){
      pushMatrix();
      for(int i=0; i<12; i++){
        stroke(0,100);
        line(30+i*17.5f,ypos-7.5f,30+i*17.5f,ypos+7.5f);
      }
      
      noStroke();
      fill(204);
      rectMode(CENTER);
      rect(swidth/2+20, ypos, swidth, sheight/2);
      if (over || locked) {
        fill(0, 0, 0);
      } else {
        fill(75, 75, 75);
      }
      rect(spos, ypos, sheight, sheight);
      popMatrix();
    }
  }

  public float getPos() {
    // Convert spos to be values between
    // 0 and the total width of the scrollbar
    return spos * ratio;
  }
  
  public void checkHourSlider(){
    for(int i=0; i<12; i++){
      if(spos-sheight/2 < 30+i*17.5f && spos+sheight/2 >30+i*17.5f){
      }
    }
  }
}
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
  
  public void clocksUpdate(){
    clocks += 3;
  }
  
  public void clocksTicking(){
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
  
  public void continueDate(){
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
  
  public void selectHour(){
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
class TripRun{
  float x,y;
  float x1,y1;
  int index;
  float[] trackX,trackY;
  float[] trailX, trailY;
  float speedX,speedY;
  float mapWidth, mapHeight;
  int time;
  float oPointX, dPointX, oPointY, dPointY;
  TripRun(String time_){
    speedX = 0;
    speedY = 0;
    index = 0;
    trackX = new float[20];
    trackY = new float[20];
    trailX = new float[200];
    trailY = new float[200];
    time = PApplet.parseInt(time_);
    
  }
  public void initMap(){
    if(mapScreenWidth/mapScreenHeight > 1.5f){
      mapWidth = mapScreenWidth*0.543077f;
    }else{
      mapWidth = mapScreenWidth;
    }
    mapHeight = mapScreenHeight;
  }
  
  public void initTrip(String[] s){
    if(index<tripRunWaypoint.length-3){
      x = mapWidth*(PApplet.parseFloat(s[index+1])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
      y = mapHeight - mapHeight*(PApplet.parseFloat(s[index])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
      x1 = mapWidth*(PApplet.parseFloat(s[index+3])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
      y1 = mapHeight - mapHeight*(PApplet.parseFloat(s[index+2])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
    }
    oPointX = mapWidth*(PApplet.parseFloat(s[1])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
    oPointY = mapHeight - mapHeight*(PApplet.parseFloat(s[0])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
    dPointX = mapWidth*(PApplet.parseFloat(s[s.length-1])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
    dPointY = mapHeight - mapHeight*(PApplet.parseFloat(s[s.length-2])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
    
  }
  
  public void display(){
    float rad = 4;
    float freq = 30;
    rotateX(240*TWO_PI/height);
    trackX[trackX.length-1] = -(mapWidth/2-x);
    trackY[trackY.length-1] = -(height/2-y);
    for(int i=0; i<trackX.length-1; i++){
      trackX[i] = trackX[i+1];
      trackY[i] = trackY[i+1];
    }
    
    trailX[trailX.length-1] = -(mapWidth/2-x);
    trailY[trailY.length-1] = -(height/2-y);
    for(int i=0; i<trailX.length-1; i++){
      trailX[i] = trailX[i+1];
      trailY[i] = trailY[i+1];
    }
    if(x == dPointX && y == dPointY){
      freq += 20;
    }
    pushMatrix();
      translate(trackX[trackX.length-1],trackY[trackY.length-1],5);
      noStroke();
      fill(255,0,0,150);
      sphere(3);
    popMatrix();
    
    translate(0,0,5);
    rotateX((10+TWO_PI)/height);
    for(int i=1; i<trackX.length; i++){
      fill(statistics[times.start_date].lineColor[times.start_date]);
      noStroke();
      ellipse(trackX[i],trackY[i],rad/2,rad/2);
    }
    for(int i=1; i<trailX.length; i++){
        fill(255,255,0,freq);
        noStroke();
        ellipse(trailX[i],trailY[i],rad/2,rad/2);
    }
    
    pushMatrix();
      translate(-(mapWidth/2-dPointX),-(height/2-dPointY));
      noStroke();
      fill(255,150);
      sphere(3);
    popMatrix();
  }

  public void update(){
    x = x+speedX;
    y = y+speedY;
  }
  
  public void directionTrip(){
    directionX();
    display();
    directionY();
    if(speedX==0 && speedY==0){
      index+=2;
    }
    if(index>tripRunWaypoint.length-3){
      index =0;
    }
  }
  
  public void directionX(){
    if(!timePauseButton){
      if(abs(x1-x)>0.7f && speedY==0){
        if(x<x1){
          speedX += 1;
        }else if(x>x1){
          speedX -= 1;
        }
      }else{
        speedX = 0;
        x = x1;
      }
    }
  }
  
  public void directionY(){
    if(!timePauseButton){
      if(abs(y1-y)>0.7f && speedX==0){
        if(y<y1){
          speedY += 1;
        }else if(y>y1){
          speedY -= 1;
        }
      }else{
        speedY = 0;
        y = y1;
      }
    }
  }
  
}
  public void settings() {  size(1000, 960, P3D);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "data_visualization" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
