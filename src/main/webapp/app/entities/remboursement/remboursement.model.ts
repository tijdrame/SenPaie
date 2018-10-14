import { BaseEntity, User } from './../../shared';

export class Remboursement implements BaseEntity {
    constructor(
        public id?: number,
        public dateRemboursement?: any,
        public montant?: number,
        public deleted?: boolean,
        public isRembourse?: boolean,
        public detailPret?: BaseEntity,
        public userCreated?: User,
        public userUpdated?: User,
        public bulletins?: BaseEntity[],
    ) {
        this.deleted = false;
        this.isRembourse = false;
    }
}
