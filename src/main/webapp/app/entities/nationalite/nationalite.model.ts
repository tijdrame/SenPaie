import { BaseEntity } from './../../shared';

export class Nationalite implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public deleted?: boolean,
    ) {
        this.deleted = false;
    }
}
