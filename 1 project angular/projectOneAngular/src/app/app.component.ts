import { Component, ElementRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],

})
export class AppComponent {
  private apiUrl = 'http://localhost:8080/api/qna/ask';

  constructor(private http: HttpClient) { }

  messages: { sender: string; text: string }[] = [];
  userInput: string = '';
  loading: boolean = false;

  @ViewChild('chatWindow') chatWindow!: ElementRef;
  sendMessage() {
    if (!this.userInput.trim()) return;

    const userMessage = { sender: 'User', text: this.userInput };
    this.messages.push(userMessage);

    this.scrollToBottom();
    this.loading = true; // Show typing indicator

    this.http.post('http://localhost:8080/api/qna/ask', { question: this.userInput })
      .subscribe(
        (response: any) => {
          this.loading = false;
          const botMessage = { sender: 'Bot', text: response.candidates[0].content.parts[0].text };
          this.messages.push(botMessage);
          this.scrollToBottom();
        },
        (error: any) => {
          this.loading = false;
          this.messages.push({ sender: 'Bot', text: 'Error: Unable to get a response' });
        }
      );

    this.userInput = '';
  }

  scrollToBottom() {
    setTimeout(() => {
      if (this.chatWindow) {
        this.chatWindow.nativeElement.scrollTo({
          top: this.chatWindow.nativeElement.scrollHeight,
          behavior: 'smooth' // Smooth scrolling effect
        });
      }
    }, 100);
  }

}
