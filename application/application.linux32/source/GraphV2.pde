class GraphV2{
  String[] dataTime;
  String[] dataLine;
  String[] dateName;
  int[] lineData;
  color[] lineColor;
  color graphColor;
  int filterMin, filterMax, filterSwap;
  int min, max;
  int dateMin, dateMax;
  int sumDate, avgDate;
  
  GraphV2(int index){
    dataTime = loadStrings(url_timeOutput[index]);
    dataLine = splitTokens(dataTime[0],",\"");
    lineData = new int[24];
    lineColor = new color[7];
    dateName = new String[7];
    graphColor = color(150,0);
    dateMin = dataLine.length;
    min = 0;
  }
  void secondGraphSetup(){
    if(showGraphButton){
      noStroke();
      pushMatrix();
      if(expandGraphButton){
        if(mapScreenWidth/mapScreenHeight < 1.5){
          fill(graphColor);
          rectMode(CORNERS);
          rect(350,height/1.0422,850,height/1.275);
          
          stroke(0);
          strokeWeight(3);
          line(350,height/1.0422,850,height/1.0422);
          line(350,height/1.0422,350,height/1.275);
          
          textFont(f2, 15);
          fill(255);
          text("Total of trips in each day (7 days)", 500, height/1.280);
        }
      }
      if(mapScreenWidth/mapScreenHeight > 1.5){
          fill(graphColor);
          rectMode(CORNERS);
          rect(width/2+300,height/1.0422,width/2+800,height/1.275);
          
          stroke(0);
          strokeWeight(3);
          line(width/2+300,height/1.0422,width/2+800,height/1.0422);
          line(width/2+300,height/1.0422,width/2+300,height/1.275);
          
          textFont(f2, 15);
          fill(255);
          text("Total of trips in each day (7 days)", width/2+450, height/1.280);
      }
        popMatrix();
    }
  }
  
  void initLineGraph(){
    for(int i=0; i<dataLine.length; i++){
      for(int j=0; j<24; j++){
        if(int(dataLine[i]) == j){
          lineData[j] += 1;
        }
      }
    }
    lineColor[0] = color(#F7F65E); //Monday
    lineColor[1] = color(#EC4EAE); //Tuesday
    lineColor[2] = color(#35B41E); // Wednesday
    lineColor[3] = color(#E58B26); // Thursday
    lineColor[4] = color(#20C9D5); // Friday
    lineColor[5] = color(#A92DDA); // Saturday
    lineColor[6] = color(#F43A3A); // Sunday
    
    dateName[0] = "Monday";
    dateName[1] = "Tuesday";
    dateName[2] = "Wednesday";
    dateName[3] = "Thursday";
    dateName[4] = "Friday";
    dateName[5] = "Saturday";
    dateName[6] = "Sunday";
  }
  
  void secondGraphBG(){
    if(showGraphButton){
      if(expandGraphButton){
        //background
        fill(100);
        noStroke();
        rectMode(LEFT);
        rect(10,height/1.02,width-10,height/1.322);
      }
    }
  }
  
  void secGrph_line(int date,int inHour){
    if(showGraphButton){
      if(expandGraphButton || mapScreenWidth/mapScreenHeight > 1.5){
        float xAxis = 0;
        float yAxis = height/1.05;
        if(mapScreenWidth/mapScreenHeight > 1.5){
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
  
  void graphStatic(){
      if(showGraphButton){
        sumDate = 0;
        int[] grphStat_lineData = new int[24];
        for(int i=0; i<dataLine.length; i++){
          for(int j=0; j<24; j++){
            if(int(dataLine[i]) == j){
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
  
  void filterLineGraph(){
    if(showGraphButton){
      textFont(f2,15);
      if(expandGraphButton){
        fill(255,25);
        rectMode(LEFT);
        rect(855,(height/1.2)-15,985,(height/1.2)+45);
        if((mouseX > 875-10 && mouseX < 875+10) && (mouseY > (height/1.2)-10 && mouseY < (height/1.2)+10)){
          fill(255);
          text("Default", mouseX-20, mouseY-20);
        }else{
          fill(150);
        }
        rectMode(CENTER);
        pushMatrix();
          translate(875,height/1.2);
          rect(0,0,20,20);
        popMatrix();
        for(int i=1; i<statistics.length+1; i++){
          if(i-1<(statistics.length-1)/2){
            if((mouseX > 875+(i*30)-10 && mouseX < 875+(i*30)+10) && (mouseY > (height/1.2)-10 && mouseY < (height/1.2)+10)){
              fill(statistics[i-1].lineColor[i-1],250);
              text(statistics[i-1].dateName[i-1],mouseX-((i*i)*5), mouseY-20);
            }else{
              fill(statistics[i-1].lineColor[i-1],100);
            }
            pushMatrix();
              translate(875+(i*30),height/1.2);
              rect(0,0,20,20);
            popMatrix();
          }else{
            if((mouseX > 875+((i-4)*30)-10 && mouseX < 875+((i-4)*30)+10) && (mouseY > (height/1.2)+20 && mouseY < (height/1.2)+40)){
              fill(statistics[i-1].lineColor[i-1],250);
              text(statistics[i-1].dateName[i-1],mouseX-(i*5), mouseY+40);
            }else{
              fill(statistics[i-1].lineColor[i-1],100);
            }
            pushMatrix();
              translate(875+((i-4)*30),(height/1.2)+30);
              rect(0,0,20,20);
            popMatrix();
          }
        }
      }
      if(mapScreenWidth/mapScreenHeight > 1.5){
        fill(255,25);
        rectMode(LEFT);
        rect(width/2+805,(height/1.2)-15,width/2+935,(height/1.2)+45);
        if((mouseX > width/2+825-10 && mouseX < width/2+825+10) && (mouseY > (height/1.2)-10 && mouseY < (height/1.2)+10)){
          fill(255);
          text("Default", mouseX-20, mouseY-20);
        }else{
          fill(150);
        }
        rectMode(CENTER);
        pushMatrix();
          translate((width/2)+825,height/1.2);
          rect(0,0,20,20);
        popMatrix();
        for(int i=1; i<statistics.length+1; i++){
          if((mouseX > width/2+825+(i*30)-10 && mouseX < width/2+825+(i*30)+10) && (mouseY > (height/1.2)-10 && mouseY < (height/1.2)+10)){
            fill(statistics[i-1].lineColor[i-1],250);
            text(statistics[i-1].dateName[i-1],mouseX-((i*i)*5), mouseY-20);
          }else{
            fill(statistics[i-1].lineColor[i-1],150);
          }
          if(i-1<(statistics.length-1)/2){
            pushMatrix();
            translate(width/2+825+(i*30),height/1.2);
            rect(0,0,20,20);
            popMatrix();
          }else{
            if((mouseX > width/2+825+((i-4)*30)-10 && mouseX < width/2+825+((i-4)*30)+10) && (mouseY > (height/1.2)+20 && mouseY < (height/1.2)+40)){
              fill(statistics[i-1].lineColor[i-1],250);
              text(statistics[i-1].dateName[i-1],mouseX-(i*5), mouseY+40);
            }else{
              fill(statistics[i-1].lineColor[i-1],150);
            }
            pushMatrix();
            translate(width/2+825+((i-4)*30),(height/1.2)+30);
            rect(0,0,20,20);
            popMatrix();
          }
        }
      }
    }
  }
  
  void filterNumberGraph(){
    if(showGraphButton){
      if(expandGraphButton){
        if((mouseX > 870-10 && mouseX < 870+10) && (mouseY > (height/1.105)-12.5) && mouseY < (height/1.105)+12.5){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(870,(height/1.105),20,25);
        
        if((mouseX > 930-10 && mouseX < 930+10) && (mouseY > (height/1.105)-12.5) && mouseY < (height/1.105)+12.5){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(930,(height/1.105),20,25);
        
        fill(50);
        rect(900,(height/1.105),30,30);
        fill(255);
        textFont(f2,20);
        text(filterMin,892.5,(height/1.105)+7.5);
        
        if((mouseX > 870-10 && mouseX < 870+10) && (mouseY > (height/1.055)-12.5) && mouseY < (height/1.055)+12.5){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(870,(height/1.055),20,25);
        
        if((mouseX > 930-10 && mouseX < 930+10) && (mouseY > (height/1.055)-12.5) && mouseY < (height/1.055)+12.5){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(930,(height/1.055),20,25);
  
        if((mouseX > 965-15 && mouseX < 965+15) && (mouseY > (height/1.075)-25) && mouseY < (height/1.075)+25){
          fill(0,255,0,250);
        }else{
          fill(0,255,0,50);
        }
        rect(965,(height/1.075),30,50);
        
        fill(50);
        rect(900,(height/1.055),30,30);
        fill(255);
        textFont(f2,20);
        text(filterMax,892.5,(height/1.055)+7.5);
        
      }
      if(mapScreenWidth/mapScreenHeight > 1.5){
        if((mouseX > width/2+820-10 && mouseX < width/2+820+10) && (mouseY > (height/1.105)-12.5) && mouseY < (height/1.105)+12.5){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(width/2+820,(height/1.105),20,25);
        
        if((mouseX > width/2+880-10 && mouseX < width/2+880+10) && (mouseY > (height/1.105)-12.5) && mouseY < (height/1.105)+12.5){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(width/2+880,(height/1.105),20,25);
        
        fill(50);
        rect(width/2+850,(height/1.105),30,30);
        fill(255);
        textFont(f2,20);
        text(filterMin,width/2+842.5,(height/1.105)+7.5);
        
        if((mouseX > width/2+820-10 && mouseX < width/2+820+10) && (mouseY > (height/1.055)-12.5) && mouseY < (height/1.055)+12.5){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(width/2+820,(height/1.055),20,25);
        
        if((mouseX > width/2+880-10 && mouseX < width/2+880+10) && (mouseY > (height/1.055)-12.5) && mouseY < (height/1.055)+12.5){
          fill(255,250);
        }else{
          fill(255,50);
        }
        rect(width/2+880,(height/1.055),20,25);
        
        if((mouseX > width/2+915-10 && mouseX < width/2+915+10) && (mouseY > (height/1.075)-25) && mouseY < (height/1.075)+25){
          fill(0,255,0,250);
        }else{
          fill(0,255,0,50);
        }
        rect(width/2+915,(height/1.075),30,50);
        
        fill(50);
        rect(width/2+850,(height/1.055),30,30);
        fill(255);
        textFont(f2,20);
        text(filterMax,width/2+842.5,(height/1.055)+7.5);
      }
    }
  }
}
