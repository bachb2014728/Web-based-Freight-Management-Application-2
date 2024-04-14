const host = 'https://vnprovinces.pythonanywhere.com/api/provinces';
function fetchDataStart(api, selectId) {
    axios.get(api)
        .then(response => {
            const data = response.data.results;
            const select = document.getElementById(selectId);

            // Clear previous options
            select.innerHTML = '';

            data.forEach(item => {
                const option = document.createElement('option');
                option.value = item.name;
                option.id = item.id;
                option.text = item.name;
                select.add(option);
            });
        })
        .catch(error => console.error(error));
}

function fetchDataEnd(api, selectId) {
    axios.get(api)
        .then(response => {
            const data = response.data.results;
            const select = document.getElementById(selectId);

            // Clear previous options
            select.innerHTML = '';

            data.forEach(item => {
                const option = document.createElement('option');
                option.value = item.name;
                option.id = item.id;
                option.text = item.name;
                select.add(option);
            });
        })
        .catch(error => console.error(error));
}

function fetchDistricts(api, selectId) {
    axios.get(api)
        .then(response => {
            const data = response.data.districts;
            console.log(response.data)
            const select = document.getElementById(selectId);

            // Clear previous options
            select.innerHTML = '';

            data.forEach(item => {
                const option = document.createElement('option');
                option.value = item.name;
                option.id = item.id;
                option.text = item.name;
                select.add(option);
            });
        })
        .catch(error => console.error(error));
}
function fetchWards(api, selectId) {
    axios.get(api)
        .then(response => {
            const data = response.data.wards;
            const select = document.getElementById(selectId);

            // Clear previous options
            select.innerHTML = '';

            data.forEach(item => {
                const option = document.createElement('option');
                option.value = item.name;
                option.id = item.id;
                option.text = item.name;
                select.add(option);
            });
        })
        .catch(error => console.error(error));
}

document.getElementById('provincesStart').addEventListener('change', function() {
    const selectedOption = this.options[this.selectedIndex];
    const provinceId = selectedOption.getAttribute('id');
    fetchDistricts(`${host}/${provinceId}`, 'districtsStart');
    // Clear wards
    document.getElementById('wardsStart').innerHTML = '';
});

document.getElementById('provincesEnd').addEventListener('change', function() {
    const selectedOption = this.options[this.selectedIndex];
    const provinceId = selectedOption.getAttribute('id');
    fetchDistricts(`${host}/${provinceId}`, 'districtsEnd');
    // Clear wards
    document.getElementById('wardsEnd').innerHTML = '';
});
document.getElementById('districtsStart').addEventListener('change', function() {
    const selectedOption = this.options[this.selectedIndex];
    const districtId = selectedOption.getAttribute('id');
    fetchWards(`https://vnprovinces.pythonanywhere.com/api/districts/${districtId}`, 'wardsStart');
});
document.getElementById('districtsEnd').addEventListener('change', function() {
    const selectedOption = this.options[this.selectedIndex];
    const districtId = selectedOption.getAttribute('id');
    fetchWards(`https://vnprovinces.pythonanywhere.com/api/districts/${districtId}`, 'wardsEnd');
});

fetchDataStart(`${host}?basic=true&limit=100`, 'provincesStart');
fetchDataEnd(`${host}?basic=true&limit=100`, 'provincesEnd');