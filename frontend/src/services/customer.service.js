import createApiClient from "./api.service";
class CustomerService {
    constructor(baseUrl = "http://localhost:8000/api/v1/customer") {
        this.api = createApiClient(baseUrl);
        this.token = localStorage.getItem('token');
    }
    async get() {
        return (await this.api.get("/",{
            headers: {
                'Authorization': `Bearer ${this.token}`
            }
        }));
    }
    async update(data) {
        return (await this.api.put("/update",data,{
            headers: {
                'Authorization': `Bearer ${this.token}`
            }
        }));
    }
}
export default new CustomerService();