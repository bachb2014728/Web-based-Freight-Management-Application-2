import createApiClient from "./api.service";
class MerchandiseService {
    constructor(baseUrl = "http://localhost:8000/api/v1/merchandises") {
        this.api = createApiClient(baseUrl);
        this.token = localStorage.getItem('token');
    }
    async getAllMerchandises(){
        try {
            return (await this.api.get("",{
                headers: {
                    'Authorization': `Bearer ${this.token}`
                }
            }));
        }catch (e) {
            console.log(e.data);
        }
    }
    async getOneMerchandise(id){
        return (await this.api.get(`/${id}`,{
            headers: {
                'Authorization': `Bearer ${this.token}`
            }
        }));
    }
    async updateOneMerchandise(id,data){
        return (await this.api.put(`/${id}`,data,{
            headers: {
                'Authorization': `Bearer ${this.token}`
            }
        }))
    }
    async updateOneMerchandiseAddress(id,data){
        return (await this.api.put(`/${id}/address`,data,{
            headers: {
                'Authorization': `Bearer ${this.token}`
            }
        }))
    }
    async createOneMerchandise(data){
        return (await this.api.post("", data));
    }
    async deleteOneMerchandise(data){
        return (await this.api.delete(`/${data}`,{
            headers: {
                'Authorization': `Bearer ${this.token}`
            }
        }));
    }
}
export default new MerchandiseService();