import { BaseEntity } from './../../shared';

export class Exercice implements BaseEntity {
    constructor(
        public id?: number,
        public debutExercice?: number,
        public finExercice?: number,
        public actif?: boolean,
        public deleted?: boolean,
    ) {
        this.actif = false;
        this.deleted = false;
    }
}
