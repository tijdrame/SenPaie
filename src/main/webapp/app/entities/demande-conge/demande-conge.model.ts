import { BaseEntity, User } from './../../shared';

export class DemandeConge implements BaseEntity {
    constructor(
        public id?: number,
        public dateCreated?: any,
        public dateDebut?: any,
        public dateFin?: any,
        public motifRejet?: string,
        public deleted?: boolean,
        public libelle?: string,
        public statutRH?: BaseEntity,
        public statutDG?: BaseEntity,
        public collaborateur?: BaseEntity,
        public userCreated?: User,
        public userUpdated?: User,
        public userDeleted?: User,
        public typeAbsence?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
