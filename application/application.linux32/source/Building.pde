class Building{
  float h;
  float posX;
  float posY;
  
  Building(float posX_, float posY_ , float h_){
    h = h_;
    if(width/height >1.5){
      posX = posX_*1.05;
    }else{
      posX = posX_;
    }
    
    if(height ==1001){
      posY = posY_*1.05;
    }else{
      posY = posY_;
    }
  }
  
  Building(){
  
  }
  
  void drawBuildingTall(){
    pushMatrix();
      translate(posX,-h/2,posY);
      fill(255,0,255,h*4);
      box(5, h, 5);
    popMatrix();
  }
  
  void drawBuildingUniversity(){
    pushMatrix();
      translate(posX,-h/2,posY);
      fill(255,0,255,200);
      box(h*5, h, h*5);
    popMatrix();
    
    pushMatrix();
      translate(posX/0.95,-h,posY/0.875);
      fill(255,0,255,200);
      box(h*8, h*3, h*2.5);
    popMatrix();
  }
  
  void drawBuildingHospital(){
    pushMatrix();
      translate(posX,-h,posY);
      fill(255,0,255,100);
      box(h, h*2, h*2);
    popMatrix();
  }
  
  void drawBuildingMall(){
    pushMatrix();
      translate(posX,-h/2,posY);
      fill(255,0,255,100);
      box(h*2, h/2, h*1.5);
    popMatrix();
  }
  
  void initDowntown(Building[] buildingsDowntown){
    buildingsDowntown[0] = new Building(-40,-50,20);
    buildingsDowntown[1] = new Building(-32.5,-50,15);
    buildingsDowntown[2] = new Building(-25,-50,25);
    buildingsDowntown[3] = new Building(-17.5,-50,30);
    buildingsDowntown[4] = new Building(-30,-60,25);
    buildingsDowntown[5] = new Building(-20,-60,40);
    buildingsDowntown[6] = new Building(-47.5,-50,22);
    buildingsDowntown[7] = new Building(-10,-50,25);
    buildingsDowntown[8] = new Building(2.5,-50,30);
    buildingsDowntown[9] = new Building(10,-50,23);
    buildingsDowntown[10] = new Building(-30,-40,23);
    buildingsDowntown[11] = new Building(-20,-40,25);
    buildingsDowntown[12] = new Building(-47.5,-40,20);
    buildingsDowntown[13] = new Building(-10,-40,30);
    buildingsDowntown[14] = new Building(2.5,-40,27);
    buildingsDowntown[15] = new Building(10,-40,20);
    buildingsDowntown[16] = new Building(2.5,-60,20);
    buildingsDowntown[17] = new Building(-42,-60,20);
    buildingsDowntown[18] = new Building(-55,-60,20);
    buildingsDowntown[19] = new Building(-37.5,-32.5,15);
    buildingsDowntown[20] = new Building(-47.5,-32.5,15);
    buildingsDowntown[21] = new Building(-71.5,-28,15);
    buildingsDowntown[22] = new Building(-71.5,-35,18);
    buildingsDowntown[23] = new Building(-79,-28,10);
    buildingsDowntown[24] = new Building(-80,-35,20);
    buildingsDowntown[25] = new Building(-22.5,-32.5,22);
    buildingsDowntown[26] = new Building(-7,-29.5,22);
    buildingsDowntown[27] = new Building(-74,-51.5,25);
    buildingsDowntown[28] = new Building(-90,-51,15);
    buildingsDowntown[29] = new Building(-97.5,-51,15);
    buildingsDowntown[30] = new Building(-37.5,-20,17);
    buildingsDowntown[31] = new Building(-15,-20,15);
    buildingsDowntown[32] = new Building(-23,-20,15);
    buildingsDowntown[33] = new Building(-48.5,5,12);
    buildingsDowntown[34] = new Building(-35,-2,17.5);
    buildingsDowntown[35] = new Building(-82,0,12);
    buildingsDowntown[36] = new Building(-75,0,15);
    buildingsDowntown[37] = new Building(-15,-5,12);
    buildingsDowntown[38] = new Building(-5,-15,15);
  }
  
  void initHospital(Building[] hospital){
    hospital[0] = new Building(-223,-140.5,7);
    hospital[1] = new Building(-92.5,120,5);
    hospital[2] = new Building(17.5,-50,3);
    hospital[3] = new Building(3,-30,3);
    hospital[4] = new Building(-16,-30,3);
    hospital[5] = new Building(-180,60,6);
    hospital[6] = new Building(-50,222.5,10);
    hospital[7] = new Building(-32.5,100,5);
    hospital[8] = new Building(46,-237.5,7);
    hospital[9] = new Building(70,-237.5,7);
    hospital[10] = new Building(45,-170,5);
    hospital[11] = new Building(-16,-97.5,5);
    hospital[12] = new Building(-22.5,-97.5,5);
    hospital[13] = new Building(213,-232.5,4);
    hospital[14] = new Building(-109,-92.5,4);
    hospital[15] = new Building(-80,-95,5);
  }
  
  void initMall(Building[] mall){
    mall[0] = new Building(-277.5,33.5,6);
    mall[1] = new Building(-277.5,48,7);
    mall[2] = new Building(-62.5,-51,5);
    mall[3] = new Building(-60,-30,7);
    mall[4] = new Building(17.5,-27.5,8);
    mall[5] = new Building(17.5,-15.5,7);
    mall[6] = new Building(-190,60,5);
    mall[7] = new Building(25,-39,4);
    mall[8] = new Building(34,-39,4);
    mall[9] = new Building(26,-49,4);
    mall[10] = new Building(37,-47,4);
    mall[11] = new Building(-94,-31,7);
    mall[12] = new Building(-110,-31,7);
    mall[13] = new Building(-122.5,-30,4);
    mall[14] = new Building(-122.5,-37,4);
    mall[15] = new Building(-46,-20,4);
    mall[16] = new Building(-60,-20,4);
    mall[17] = new Building(-70,-20,4);
    mall[18] = new Building(-80,-20,4);
    mall[19] = new Building(-92.5,-20,4);
    mall[20] = new Building(-102.5,-20,4);
    mall[21] = new Building(-112.5,-20,4);
    mall[22] = new Building(-122.5,-20,4);
    mall[23] = new Building(-92.5,-13,4);
    mall[24] = new Building(-102.5,-13,4);
    mall[25] = new Building(-92.5,-6,4);
    mall[26] = new Building(-102.5,-6,4);
    mall[27] = new Building(-117.5,-10,7);
    mall[28] = new Building(-65,-10,5.5);
    mall[29] = new Building(-65,2,5.5);
    mall[30] = new Building(-78.5,-10,5.5);
    mall[31] = new Building(-78.5,2,5.5);
    mall[31] = new Building(-48.5,-12,4);
    mall[32] = new Building(-50.5,-5,4);
    mall[33] = new Building(-3,-8,3.5);
    mall[34] = new Building(25,38.5,5);
    mall[35] = new Building(14,38.5,5);
    mall[36] = new Building(27,30,3.5);
    mall[37] = new Building(19,30,3.5);
    mall[38] = new Building(11,30,3.5);
    mall[39] = new Building(27,23,3.5);
    mall[40] = new Building(19,23,3.5);
    mall[41] = new Building(11,23,3.5);
    mall[42] = new Building(27,16,3.5);
    mall[43] = new Building(19,16,3.5);
    mall[44] = new Building(11,16,3.5);
    mall[45] = new Building(13,6,6);
    mall[46] = new Building(25,7,5);
    mall[47] = new Building(35,8,4);
    mall[48] = new Building(61,41,3.5);
    mall[49] = new Building(53,41,3.5);
    mall[50] = new Building(45,41,3.5);
    mall[51] = new Building(37,41,3.5);
    mall[52] = new Building(61,34,3.5);
    mall[53] = new Building(53,34,3.5);
    mall[54] = new Building(45,34,3.5);
    mall[55] = new Building(37,34,3.5);
    mall[56] = new Building(69,27,3.5);
    mall[57] = new Building(61,27,3.5);
    mall[58] = new Building(53,27,3.5);
    mall[59] = new Building(45,27,3.5);
    mall[60] = new Building(52,18,6);
    mall[61] = new Building(67,18,6);
    mall[62] = new Building(69,9,3.5);
    mall[63] = new Building(56,-33,3.5);
    mall[64] = new Building(56,-26,3.5);
    mall[65] = new Building(56,-19,3.5);
    mall[66] = new Building(56,-12,3.5);
    mall[67] = new Building(56,-5,3.5);
    mall[68] = new Building(48,-8.5,3.5);
    mall[69] = new Building(41,-1.5,3.5);
    mall[70] = new Building(35,-12,3.5);
  }
  void initNorthMall(Building[] northMall){
    northMall[0] = new Building(-78.5,-121,5);
    northMall[1] = new Building(-67.5,-121,5);
    northMall[2] = new Building(-52.5,-121,5);
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
    northMall[14] = new Building(23.5,-98.5,5);
    northMall[15] = new Building(17.5,-109,3.5);
    northMall[16] = new Building(26,-109,3.5);
    northMall[17] = new Building(18,-72.5,4);
    northMall[18] = new Building(51.5,-109,5);
    northMall[19] = new Building(90,-77.5,4);
    northMall[20] = new Building(30,-80,4);
  }
  
  void initSouthMall(Building[] southMall){
    southMall[0] = new Building(-35,242.5,5);
    southMall[1] = new Building(-35,232.5,5);
    southMall[2] = new Building(-50,240,8);
    southMall[3] = new Building(-35,222.5,5);
    southMall[4] = new Building(-35,212.5,5);
    southMall[5] = new Building(-15,242.5,4);
    southMall[6] = new Building(-5,242.5,4);
    southMall[7] = new Building(47.5,155,4);
    southMall[8] = new Building(37.5,145,4);
    southMall[9] = new Building(-21,95.5,3.5);
    southMall[10] = new Building(-12.5,95.5,3.5);
  }
}
