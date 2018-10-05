import { BaseEntity, User } from './../../shared';

export class DemandeConge implements BaseEntity {
    constructor(
        public id?: number,
        public dateCreated?: any,
        public dateDebut?: any,
        public dateFin?: any,
        public motifRejet?: string,
        public deleted?: boolean,
        public statutRH?: BaseEntity,
        public statutDG?: BaseEntity,
        public collaborateur?: BaseEntity,
        public userCreated?: User,
        public userUpdated?: User,
        public userDeleted?: User,
    ) {
        this.deleted = false;
    }
}
