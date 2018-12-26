import { BaseEntity } from './../../shared';

export class Prime implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public code?: string,
        public imposable?: boolean,
        public deleted?: boolean,
    ) {
        this.imposable = false;
        this.deleted = false;
    }
}
