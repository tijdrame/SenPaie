import { BaseEntity } from './../../shared';

export class SituationMatrimoniale implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public code?: string,
        public deleted?: boolean,
        public nbParts?: number,
    ) {
        this.deleted = false;
    }
}
