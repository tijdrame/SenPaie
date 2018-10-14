import { BaseEntity } from './../../shared';

export class Statut implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public code?: string,
        public deleted?: boolean,
    ) {
        this.deleted = false;
    }
}
