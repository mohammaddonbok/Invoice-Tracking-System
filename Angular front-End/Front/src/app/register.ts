
export class Register {
  firstName!: string;
  lastName!: string;
  email!: string;
  password!: string;
  roles!: string;

  clear() {
    this.firstName = '';
    this.lastName = '';
    this.email = '';
    this.password = '';
    this.roles = '';
  }
}
