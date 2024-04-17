import createApiClient from "./api.service";
class AuthService {
    constructor(baseUrl = "/api/v1/auth") {
        this.api = createApiClient(baseUrl);
    }
    async signup(data) {
        return (await this.api.post("/signup", data));
    }
    async login(data) {
        return (await this.api.post("/login", data));
    }
    async refresh(data) {
        return (await this.api.post("/refresh", data ,{
                withCredentials: true
            })
        );
    }
    async profile(data){
        return (await  this.api.get("/profile", data));
    }
}
export default new AuthService();