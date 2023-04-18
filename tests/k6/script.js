import http from 'k6/http';
import { check, sleep } from 'k6';
import { SharedArray } from 'k6/data';
import papaparse from 'https://jslib.k6.io/papaparse/5.1.1/index.js';
import { vu } from 'k6/execution';


const url = 'http://192.168.40.50:3000/create';

export const options = {
    scenarios :{
      "use-all-the-data": {
        executor: "per-vu-iterations",
        vus: 10,
        iterations: 50,
        maxDuration: "10s"
      }}
}
  
  
export default function () {

    var payload =  JSON.stringify(
            {

            });
    //console.log('debug: ', JSON.stringify(payload));
    



  // Using a JSON string as body
    var response = http.post(url, payload);
    console.log(response.json()); 
    check(response, {
        'is status 200': (r) => r.status === 200,
      });
    //console.log(`VU: ${__VU}  -  ITER: ${__ITER}`);


};