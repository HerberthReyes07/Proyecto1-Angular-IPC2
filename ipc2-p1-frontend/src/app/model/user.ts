import { TypeUser } from "./type-user";

export class User {
    id!: number;
    name!: string;
    username!: string;
    password!: string;
    dpi!: string;
    sex!: string;
    typeUser!: TypeUser;
    active!: boolean;
}
