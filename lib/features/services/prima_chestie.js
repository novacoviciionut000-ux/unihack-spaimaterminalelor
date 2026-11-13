// Funcția care cere traseul de la OpenRouteService
async function getRouteAndDrawMap(startLng, startLat, endLng, endLat) {
    const apiKey = 'eyJvcmciOiI1YjNjZTM1OTc4NTExMTAwMDFjZjYyNDgiLCJpZCI6IjJiYzAwZGY4NTE3YjQ0OThiMDZkMDljZTZiNTM3YThmIiwiaCI6Im11cm11cjY0In0=';
    
    // Profilul "wheelchair" ține cont de scări, borduri și pante (din datele OpenStreetMap)
    const url = 'https://api.openrouteservice.org/v2/directions/wheelchair/geojson';

    const bodyData = {
        coordinates: [
            [startLng, startLat], // Punctul de plecare (Atenție: ORS cere Longitudine, apoi Latitudine)
            [endLng, endLat]      // Destinația
        ]
    };

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Authorization': apiKey,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bodyData)
        });

        const geojsonData = await response.json();

        // Leaflet știe automat să deseneze formatul GeoJSON primit de la ORS
        L.geoJSON(geojsonData, {
            style: function (feature) {
                return { color: '#007bff', weight: 5 }; // O linie albastră, groasă de 5px
            }
        }).addTo(map); // 'map' este variabila hărții voastre Leaflet

    } catch (error) {
        console.error('Eroare la calcularea rutei:', error);
    }
}

// Apelarea funcției (ex: de la coordonatele X la coordonatele Y)
// getRouteAndDrawMap(26.0963, 44.4396, 26.1025, 44.4412);