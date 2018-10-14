import { BaseEntity, User } from './../../shared';

export class Pieces implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public dateDebut?: any,
        public dateExpiration?: any,
        public imageContentType?: string,
        public image?: any,
        public dateCreated?: any,
        public deleted?: boolean,
        public dateDeleted?: any,
        public dateUpdated?: any,
        public collaborateur?: BaseEntity,
        public user?: User,
        public userUpdated?: User,
        public userDeleted?: User,
    ) {
        this.deleted = false;
    }
}
