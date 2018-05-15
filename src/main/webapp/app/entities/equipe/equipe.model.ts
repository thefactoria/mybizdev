import { BaseEntity } from '../../shared';
import { BizDev } from '../biz-dev/biz-dev.model';

export class Equipe implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public bizDevs?: BizDev[],
    ) {
    }
}
