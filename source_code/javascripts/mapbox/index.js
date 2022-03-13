map.on('load', () => {
    map.addLayer({
        id: 'rpd_parks',
        type: 'fill',
        source: {
            type: 'vector',
            url: 'mapbox://mapbox.3o7ubwm8'
        },
        'source-layer': 'RPD_Parks',
        layout: {
            visibility: 'visible'
        },
        paint: {
            'fill-color': 'rgba(61,153,80,0.55)'
        }
    });
});
mapboxgl.accessToken = 'pk.eyJ1IjoiaGF3a2VyaWFuIiwiYSI6ImNrdHlmeWJ6MjFrcGYyd21yejg4bDN0Nm0ifQ.8YdLSTJZ-pj_2OpiNRxquQ';
