import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Chat} from "../models/Chat";
import {environment} from "../../environment/environment";
import {AdminMessage} from "../models/AdminMessage";
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private selectedUserChatIdSubject = new BehaviorSubject<number>(-1);
  selectedUserChatId$ = this.selectedUserChatIdSubject.asObservable();

  setSelectedUserChatId(chatId: number) {
    this.selectedUserChatIdSubject.next(chatId);
  }

  constructor(private http: HttpClient,
              private cookieService: CookieService) {
  }

  public getChatHistory(chatId: number) {
    return this.http.get<Chat[]>(environment.backendURL + "/chats/history/" + chatId, {
      headers: {
        "Authorization": "Bearer " + this.cookieService.get("jwt-token")
      }});
  }

  public sendMessage(chatId: number, message: AdminMessage) {
    const body = JSON.stringify(message);
    return this.http.post<void>(environment.backendURL + "/chats/" + chatId, body, {
      headers: {
        "Authorization": "Bearer " + this.cookieService.get("jwt-token"),
        "Content-Type": "application/json"
      }
    })
  }
}
