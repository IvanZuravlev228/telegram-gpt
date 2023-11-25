import {Component, OnInit} from '@angular/core';
import {UsersService} from "../../services/users.service";
import {User} from "../../models/user/User";
import {ChatService} from "../../services/chat.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit{
  users: User[] = [];

  constructor(private userService: UsersService,
              private chatService: ChatService) {
  }

  ngOnInit(): void {
    this.getAllUsers();
  }

  public getAllUsers() {
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  selectUser(chatId: number) {
    this.chatService.setSelectedUserChatId(chatId);
  }

  deleteUser(userId: number) {
    this.userService.deleteUser(userId).subscribe({
      next: () => {
        let index = this.users.findIndex(user => user.id === userId);
        if (index !== -1) {
          this.users.splice(index, 1);
        }
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
