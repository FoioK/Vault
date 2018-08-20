import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {OAuthService} from "../../service/o-auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [OAuthService]
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,
              private loginService: OAuthService) {

  }

  loginForm: FormGroup;

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      login: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  getAccessToken() {
    this.loginService.getToken(
      this.loginForm.get('login').value,
      this.loginForm.get('password').value)
      .subscribe(respone => console.log(respone));
  }
}
