import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OAuthService {

  private readonly oauthUrl: string = 'http://localhost:8081/oauth/token';

  constructor(private httpClient: HttpClient) {

  }

  getToken(login: string, password: string) {
    const credentials = 'username=' + login
      + '&password=' + password
      + '&grant_type=password';

    return this.httpClient.post(
      this.oauthUrl,
      credentials,
      {
        headers: {
          'Authorization' : 'Basic ' + btoa('vault_id:vault_secret'),
          'Content-Type' : 'application/x-www-form-urlencoded'
        },
      });
  }
}
