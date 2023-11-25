import {Component, OnDestroy} from '@angular/core';
import {ChatService} from "../../services/chat.service";
import {Subscription} from "rxjs";
import {Chat} from "../../models/Chat";
import {AdminMessage} from "../../models/AdminMessage";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnDestroy {
  selectedUserChatId: number = - 1;
  private subscription: Subscription;
  chatHistory: Chat[] = [];
  newMessage: string = "";

  constructor(private chatService: ChatService) {
    this.subscription = this.chatService.selectedUserChatId$.subscribe((chatId) => {
      this.selectedUserChatId = chatId;
      this.getChatHistory(chatId);
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  public getChatHistory(chatId: number) {
    this.chatService.getChatHistory(chatId).subscribe({
      next: (chats) => {
        this.chatHistory = chats;
        console.log(this.chatHistory);
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  sendMessage() {
    let message = new AdminMessage();
    message.message = this.newMessage;
    this.chatService.sendMessage(this.selectedUserChatId, message).subscribe({
      next: () => {
        this.newMessage = "";
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
