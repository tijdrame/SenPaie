import { BaseEntity, User } from './../../shared';

export class DetailPret implements BaseEntity {
    constructor(
        public id?: number,
        public montant?: number,
        public isRembourse?: boolean,
        public deleted?: boolean,
        public collaborateur?: BaseEntity,
        public pret?: BaseEntity,
        public userCreated?: User,
        public userUpdated?: User,
        public userDeleted?: User,
    ) {
        this.isRembourse = false;
        this.deleted = false;
    }
}
