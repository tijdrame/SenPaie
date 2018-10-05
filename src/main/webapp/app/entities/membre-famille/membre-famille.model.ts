import { BaseEntity, User } from './../../shared';

export class MembreFamille implements BaseEntity {
    constructor(
        public id?: number,
        public prenom?: string,
        public nom?: string,
        public adresse?: string,
        public isActif?: boolean,
        public deleted?: boolean,
        public dateNaissance?: any,
        public dateMariage?: any,
        public photoContentType?: string,
        public photo?: any,
        public telephone?: string,
        public collaborateur?: BaseEntity,
        public user?: User,
        public userUpdate?: User,
        public userDeleted?: User,
        public typeRelation?: BaseEntity,
    ) {
        this.isActif = false;
        this.deleted = false;
    }
}
