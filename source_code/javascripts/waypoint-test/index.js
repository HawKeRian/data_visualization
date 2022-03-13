startLat = [51.0380864,51.03474004,51.03557847,51.05732974,51.04310955];
startLng = [-114.09443,-114.09443,-114.09213,-114.09217,-114.08985];
endlat = [51.03725518,51.03642663,51.03642663,51.05482177,51.03308317];
endlng = [-114.0875356,-114.0760431,-114.0760431,-114.0898679,-114.0714416]
console.log(startLat.length);


function initMap() {
    // var i =0;
    var pointA = [];
    var pointB = [];
    pointCenter = new google.maps.LatLng(startLat[0].toFixed(1), startLng[0].toFixed(1));
    const markerArray = [];
    // Create a map and center it on pointCenter.
    const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 4,
      center: pointCenter,
      //center: { lat: 51.0, lng: -114.0 },
    });
    // Instantiate an info window to hold step text.
    const stepDisplay = new google.maps.InfoWindow();
    // Display the route between the initial start and end selections.
    
    for(i=0; i<startLat.length; i++){
      pointA[i] = new google.maps.LatLng(startLat[i], startLng[i]),
      pointB[i] = new google.maps.LatLng(endlat[i], endlng[i]);
      // Instantiate a directions service.
      const directionsService = new google.maps.DirectionsService();
      // Create a renderer for directions and bind it to the map.
      const directionsRenderer = new google.maps.DirectionsRenderer({ map: map });
      calculateAndDisplayRoute(
        directionsRenderer,
        directionsService,
        markerArray,
        stepDisplay,
        map,
        pointA[i],
        pointB[i]
      );
    }
  }
  
  function calculateAndDisplayRoute(
    directionsRenderer,
    directionsService,
    markerArray,
    stepDisplay,
    map,
    pointA,
    pointB
  ) {
    // First, remove any existing markers from the map.
    for (let i = 0; i < markerArray.length; i++) {
      markerArray[i].setMap(null);
    }
    // Retrieve the start and end locations and create a DirectionsRequest using
    // DRIVING directions.
    directionsService
      .route({
        origin: pointA,
        destination: pointB,
        //travelMode: google.maps.TravelMode.TRANSIT,Bicycling
        travelMode: google.maps.TravelMode.BICYCLING,
      })
      .then((result) => {
        // Route the directions and pass the response to a function to create
        // markers for each step.
        // document.getElementById("warnings-panel").innerHTML =
        //   "<b>" + result.routes[0].warnings + "</b>";
        directionsRenderer.setDirections(result);
        showSteps(result, markerArray, stepDisplay, map);
      })
      .catch((e) => {
        window.alert("Directions request failed due to " + e);
      });
  }
  
  function showSteps(directionResult, markerArray, stepDisplay, map) {
    // For each step, place a marker, and add the text to the marker's infowindow.
    // Also attach the marker to an array so we can keep track of it and remove it
    // when calculating new routes.
    const myRoute = directionResult.routes[0].legs[0];
    const latPos = [];
    const lngPos = [];
  
    for (let i = 0; i < myRoute.steps.length; i++) {
      const marker = (markerArray[i] =
        markerArray[i] || new google.maps.Marker());
      marker.setMap(map);
      marker.setPosition(myRoute.steps[i].start_location);
      attachInstructionText(
        stepDisplay,
        marker,
        myRoute.steps[i].instructions,
        map
      );
    }
    //Print marker's position
    // console.log('MarkerArray[0]: ',markerArray[0]);
    for(let i=0; i<markerArray.length; i++){
        latPos[i] = markerArray[i].position.lat();
        lngPos[i] = markerArray[i].position.lng();
    }
    console.log('Latitude:',latPos,'Longitude:', lngPos);
  }
  
  function attachInstructionText(stepDisplay, marker, text, map) {
    google.maps.event.addListener(marker, "click", () => {
      // Open an info window when the marker is clicked on, containing the text
      // of the step.
      stepDisplay.setContent(text);
      stepDisplay.open(map, marker);
    });
  }