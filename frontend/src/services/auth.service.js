import createApiClient from "./api.service";
class AuthService {
    constructor(baseUrl = "http://localhost:8000/api/v1/auth") {
        this.api = createApiClient(baseUrl);
        this.token = localStorage.getItem('token');
    }
    async signup(data) {
        return (await this.api.post("/signup", data));
    }
    async login(data) {
        try {
            return (await this.api.post("/login", data));
        } catch (error) {
            if (error.response && error.response.status === 401) {
                return error.response;
            } else {
                console.error(error);
            }
            throw error;
        }
    }
    async refresh(data) {
        return (await this.api.post("/refresh", data ,{
                withCredentials: true
            })
        );
    }
    async profile(){
        try {
            return (await this.api.get("/profile", {
                headers: {
                    'Authorization': `Bearer ${this.token}`
                }
            }));
        }catch (e) {
            console.log(e.data);
        }
    }
}
export default new AuthService();