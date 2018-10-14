import { BaseEntity, User } from './../../shared';

export class Pret implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public nbRemboursement?: number,
        public datePret?: any,
        public dateDebutRemboursement?: any,
        public deleted?: boolean,
        public userCreate?: User,
        public userUpdate?: User,
        public userDeleted?: User,
    ) {
        this.deleted = false;
    }
}
